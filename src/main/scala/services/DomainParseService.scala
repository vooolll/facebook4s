package services

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import org.f100ded.scalaurlbuilder.URLBuilder
import cats.implicits._
import client.FacebookClient.loginError
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import domain.oauth.HasFacebookError
import io.circe.Decoder
import serialization.FacebookDecoders.decodeOauthError
import services.DomainParseService.AppResources

import scala.concurrent.{ExecutionContext, Future}

/**
  * Transforms http domain to facebook domain
  * @param asyncRequest service for async requests
  */
class DomainParseService(asyncRequest: AsyncRequestService) extends FailFastCirceSupport {

  def sendOrFail[T, E <: HasFacebookError](uri: URLBuilder)
    (successReads: Decoder[T], failReads: Decoder[E])
    (errorFormatter: String => Future[Either[E, T]])(resources: AppResources) = {
    implicit val AppResources(system, mat, ec) = resources

    shutdownActorSystem(sendAndParseTo(uri)(successReads, failReads)(errorFormatter) map valueOrException)
  }

  def send[T, E](uri: URLBuilder)
    (successReads: Decoder[T], failReads: Decoder[E])
    (errorFormatter: String => Future[Either[E, T]])(resources: AppResources): Future[Either[E, T]] = {
    implicit val AppResources(system, mat, ec) = resources

    shutdownActorSystem(sendAndParseTo(uri)(successReads, failReads)(errorFormatter))
  }

  def shutdownActorSystem[T](f: Future[T])(implicit system: ActorSystem) = {
    f.onComplete(_ => system.terminate())(system.dispatcher)
    f
  }

  def parseResponse[E, T](response: HttpResponse)(errorFormatter: String => Future[Either[E, T]])
    (implicit reads: Decoder[T], reads1: Decoder[E],
     mat: ActorMaterializer, ec: ExecutionContext): Future[Either[E, T]] = {
    def parseFE(httpEntity: HttpEntity): Future[Either[E, T]] = Unmarshal[HttpEntity](
      httpEntity.withContentType(ContentTypes.`application/json`)).to[T] map(_.asRight) recoverWith {
      case e => errorFormatter(e.getLocalizedMessage)
    }

    def parseFL(httpEntity: HttpEntity): Future[Either[E, T]] = Unmarshal[HttpEntity](
      httpEntity.withContentType(ContentTypes.`application/json`)).to[E] map(_.asLeft) recoverWith {
      case e => errorFormatter(e.getLocalizedMessage)
    }

    response.status  match {
      case StatusCodes.OK                  => parseFE(response.entity)
      case StatusCodes.BadRequest          => parseFL(response.entity)
      case StatusCodes.InternalServerError => errorFormatter("Internal server error.")
      case _                               => errorFormatter("Unknown exception")
    }
  }


  def valueOrException[T](facebookResult: Either[HasFacebookError, T]): T = {
    facebookResult match {
      case Right(facebookResult) => facebookResult
      case Left(facebookError) => throw new RuntimeException(facebookError.error.message)
    }
  }

  private def sendAndParseTo[T, E](uri: URLBuilder)
    (successReads: Decoder[T], failReads: Decoder[E])
    (errorFormatter: String => Future[Either[E, T]])
    (implicit system: ActorSystem, mat: ActorMaterializer, ec: ExecutionContext) = {

    val entity = for {
      response <- asyncRequest.sendRequest(uri)(system, mat)
      entity <- parseResponse[E, T](response)(errorFormatter)(successReads, failReads, mat, ec)
    } yield entity

    entity.onComplete(_ => system.terminate())

    entity
  }
}

/**
  * Service that provides async requests to api, via akk http
  */
class AsyncRequestService() {
  def sendRequest(url: URLBuilder)
    (actorSystem: ActorSystem, mat: ActorMaterializer): Future[HttpResponse] = {
    Http(actorSystem).singleRequest(HttpRequest(uri = url.toString()))(mat)
  }
}

/**
  * DomainParseService helper object
  */
object DomainParseService {
  def apply() = new DomainParseService(new AsyncRequestService())
  def apply(asyncService: AsyncRequestService) = new DomainParseService(asyncService)

  case class AppResources(system: ActorSystem, mat: ActorMaterializer, ec: ExecutionContext)
}
