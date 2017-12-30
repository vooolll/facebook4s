package services

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.unmarshalling.Unmarshal
import cats.implicits._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.Decoder
import org.f100ded.scalaurlbuilder.URLBuilder

import scala.concurrent.Future

class DomainParsing(asyncRequest: AsyncRequestService) extends FailFastCirceSupport {

  def httpResponseToDomain[A](url: URLBuilder)
    (implicit entityDecoder: Decoder[A], appResources: AppResources): Future[A] = {
    shutdownActorSystem(responseToDomain(url))
  }

  def httpResponseToDomainResult[A, B](url: URLBuilder)
    (implicit entityDecoder: Decoders[A, B], appResources: AppResources): Future[Either[B, A]] = {
    shutdownActorSystem(responseToDomainResult(url))
  }

  def responseToDomainResult[A, B](url: URLBuilder)
    (implicit entityDecoder: Decoders[A, B], appResources: AppResources): Future[Either[B, A]] = {
    import appResources._
    for {
      httpResponse <- asyncRequest.sendRequest(url)
      domain       <- parseResult(responseEntityResult(httpResponse))
    } yield domain
  }

  def responseToDomain[A](url: URLBuilder)
    (implicit entityDecoder: Decoder[A], appResources: AppResources): Future[A] = {
    import appResources._
    for {
      httpResponse <- asyncRequest.sendRequest(url)
      domain       <- parse(responseEntity(httpResponse))
    } yield domain
  }

  private def responseEntity(response: HttpResponse): HttpEntity = response.status match {
    case StatusCodes.OK                  => response.entity
    case StatusCodes.BadRequest          => response.entity
    case StatusCodes.InternalServerError => throw new RuntimeException("Internal server error")
    case _                               => throw new RuntimeException("Unknown exception")
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

  private def parseResult[A, B](httpEntityResult: Either[HttpEntity, HttpEntity])
    (implicit decoders: Decoders[A, B], appResources: AppResources): Future[Either[B, A]] = {
    import appResources._
    import decoders._

    httpEntityResult match {
      case Right(httpEntity) => parse[A](httpEntity) map(a => a.asRight)
      case Left(httpEntity)  => parse[B](httpEntity) map(b => b.asLeft)
    }
  }


  private def shutdownActorSystem[T](f: Future[T])(implicit appResources: AppResources) = {
    import appResources._
    f.onComplete(_ => actorSystem.terminate())
    f
  }
}

case class Decoders[A, B](implicit val success: Decoder[A], failure: Decoder[B])