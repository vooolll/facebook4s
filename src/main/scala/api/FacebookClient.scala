package api

import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import api.FacebookClient._
import cats.implicits._
import config.FacebookConfig.{appSecret, clientId}
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import domain._
import play.api.libs.json.Reads
import services.{AsyncRequestService, URIService}

import scala.concurrent.Future

class FacebookClient(clientId: FacebookClientId, appSecret: FacebookAppSecret)
                    (implicit asyncRequestService: AsyncRequestService = AsyncRequestService(),
                      uriService: URIService = URIService()) extends PlayJsonSupport {

  import api.FacebookJsonSerializers._
  import asyncRequestService._
  import uriService._

  def appAccessToken(): Future[FacebookAccessToken] = appAccessTokenEither() map {
    case Right(facebookAccessToken) => facebookAccessToken
    case Left(facebookError) => throw new RuntimeException(facebookError.error.message)
  }

  def appAccessTokenEither(): AppAccessTokenResult = for {
    response <- sendRequest(appTokenURI)
    accessToken <- parseToJson(response)
  } yield accessToken

  def userAccessToken(code: String): AppAccessTokenResult = for {
    response <- sendRequest(userTokenURI(code))
    userAccessToken <- parseToJson(response)
  } yield userAccessToken

  private def parseToJson(response: HttpResponse) =
    parseResponse(response)(loginError)(facebookAccessTokenReads, facebookLoginErrorReads)

  private def parse[T](httpEntity: HttpEntity)(implicit reads: Reads[T]) =
    Unmarshal[HttpEntity](httpEntity.withContentType(ContentTypes.`application/json`)).to[T]

  private def parseResponse[E, T](response: HttpResponse)(errorFormatter: String => E)
    (implicit reads: Reads[T], reads1: Reads[E]): Future[Either[E, T]] = {
    response.status  match {
      case StatusCodes.OK                  => parse[T](response.entity)(reads).map(_ asRight)
      case StatusCodes.BadRequest          => parse[E](response.entity)(reads1).map(_ asLeft)
      case StatusCodes.InternalServerError => Future.successful(errorFormatter("Internal server error.") asLeft)
      case _                               => Future.successful(errorFormatter("Unknown exception") asLeft)
    }
  }

}

object FacebookClient {
  def apply(clientId: FacebookClientId, appSecret: FacebookAppSecret): FacebookClient =
    new FacebookClient(clientId, appSecret)(uriService = URIService(clientId, appSecret))

  def apply(): FacebookClient =
    new FacebookClient(clientId, appSecret)

  def apply(asyncRequestService: AsyncRequestService): FacebookClient =
    new FacebookClient(clientId, appSecret)(asyncRequestService)

  def loginError(message: String) = FacebookTokenError(FacebookError(message))

  type AppAccessTokenResult = Future[Either[FacebookTokenError, FacebookAccessToken]]
}

