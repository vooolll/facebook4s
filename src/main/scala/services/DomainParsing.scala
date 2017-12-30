package services

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.unmarshalling.Unmarshal
import cats.implicits._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import domain.oauth.HasFacebookError
import io.circe.Decoder
import org.f100ded.scalaurlbuilder.URLBuilder

import scala.concurrent.Future

class DomainParsing(asyncRequest: AsyncRequestService) extends FailFastCirceSupport {

  def httpResponseToDomain[A, B <: HasFacebookError](url: URLBuilder)
    (implicit entityDecoder: Decoders[A, B], appResources: AppResources): Future[A] = {
    import appResources.executionContext
    shutdownActorSystem(responseToDomainResult(url) map valueOrException)
  }

  def httpResponseToDomainResult[A, B <: HasFacebookError](url: URLBuilder)
    (implicit entityDecoder: Decoders[A, B], appResources: AppResources): Future[Either[B, A]] = {
    shutdownActorSystem(responseToDomainResult(url))
  }

  def responseToDomainResult[A, B <: HasFacebookError](url: URLBuilder)
    (implicit entityDecoder: Decoders[A, B], appResources: AppResources): Future[Either[B, A]] = {
    import appResources._
    for {
      httpResponse <- asyncRequest.sendRequest(url)
      domain       <- parseResult(responseEntityResult(httpResponse))
    } yield domain
  }

  private def valueOrException[A, B <: HasFacebookError](result: Either[B, A]): A = result match {
    case Right(value) => value
    case Left(error)  => throw new RuntimeException(error.error.message)
  }

  private def responseEntityResult(response: HttpResponse): Either[HttpEntity, HttpEntity] = {
    response.status match {
      case StatusCodes.OK                  => response.entity.asRight
      case StatusCodes.BadRequest          => response.entity.asLeft
      case StatusCodes.InternalServerError => throw new RuntimeException("Internal server error")
      case _                               => throw new RuntimeException("Unknown exception")
    }
  }

  private def parse[A](httpEntity: HttpEntity)
    (implicit decoder: Decoder[A], appResources: AppResources): Future[A] = {
    import appResources._
    Unmarshal[HttpEntity](httpEntity.withContentType(ContentTypes.`application/json`)).to[A].recover{
      case e => throw new RuntimeException(e.getLocalizedMessage)
    }
  }

  private def parseResult[A, B <: HasFacebookError](httpEntityResult: Either[HttpEntity, HttpEntity])
    (implicit decoders: Decoders[A, B], appResources: AppResources): Future[Either[B, A]] = {
    import appResources._
    import decoders._

    httpEntityResult match {
      case Right(httpEntity) => parse[A](httpEntity) map(a => a.asRight)
      case Left(httpEntity)  => parse[B](httpEntity) map(b => b.asLeft)
    }
  }

  private def shutdownActorSystem[A](f: Future[A])(implicit appResources: AppResources) = {
    import appResources._
    f.onComplete(_ => actorSystem.terminate())
    f
  }
}

object DomainParsing {
  def apply(asyncRequest: AsyncRequestService): DomainParsing = new DomainParsing(asyncRequest)
  def apply(): DomainParsing = new DomainParsing(new AsyncRequestService)
}

case class Decoders[A, B <: HasFacebookError](implicit val success: Decoder[A], failure: Decoder[B])