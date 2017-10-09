package services

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import api.HasFacebookError
import org.f100ded.scalaurlbuilder.URLBuilder
import play.api.libs.json.Reads
import cats.implicits._
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport

import scala.concurrent.{ExecutionContext, Future}

class DomainParseService()(asyncRequest: AsyncRequestService)
  (implicit system: ActorSystem, mat: ActorMaterializer, ec: ExecutionContext) extends PlayJsonSupport {

  def sendAndParseTo[T, E](uri: URLBuilder)(successReads: Reads[T], failReads: Reads[E])
                          (errorFormatter: String => Future[Either[E, T]]) = for {
    response <- asyncRequest.sendRequest(uri)
    userAccessToken <- parseResponse[E, T](response)(errorFormatter)(successReads, failReads)
  } yield userAccessToken

  def parseResponse[E, T](response: HttpResponse)(errorFormatter: String => Future[Either[E, T]])
    (implicit reads: Reads[T], reads1: Reads[E]): Future[Either[E, T]] = {
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
}

class AsyncRequestService()
  (implicit val system: ActorSystem, val mat: ActorMaterializer, val ec: ExecutionContext) {
  def sendRequest(url: URLBuilder): Future[HttpResponse] = {
    Http().singleRequest(HttpRequest(uri = url.toString()))
  }
}

object DomainParseService {
  def apply(implicit system: ActorSystem, mat: ActorMaterializer, ec: ExecutionContext) =
    new DomainParseService()(new AsyncRequestService())

}
