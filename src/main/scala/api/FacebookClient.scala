package api

import akka.http.scaladsl.model._
import api.FacebookClient._
import cats.implicits._
import config.FacebookConfig.{appSecret, clientId}
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import domain._
import services.{AsyncRequestService, URIService}

import scala.concurrent.Future

class FacebookClient(clientId: FacebookClientId, appSecret: FacebookAppSecret)
                    (implicit asyncRequestService: AsyncRequestService = AsyncRequestService(),
                      uriService: URIService = URIService()) extends PlayJsonSupport {

  import api.FacebookJsonSerializers._
  import asyncRequestService._
  import uriService._
  val jsonUnmarshaler = new JsonUnmarshaler()

  import jsonUnmarshaler._

  def appAccessToken(): Future[FacebookAccessToken] = appAccessTokenEither() map {
    case Right(facebookAccessToken) => facebookAccessToken
    case Left(facebookError) => throw new RuntimeException(facebookError.error.message)
  }

  def appAccessTokenEither(): FacebookAccessTokenResult =
    for {
      response <- sendRequest(appTokenURI)
      accessToken <- parseToJson(response)
    } yield accessToken

  def userAccessToken(code: String): FacebookAccessTokenResult =
    for {
      response <- sendRequest(userTokenURI(code))
      userAccessToken <- parseToJson(response)
    } yield userAccessToken

  private def parseToJson(response: HttpResponse) = parseResponse(response)(loginErrorFE)(
    facebookAccessTokenReads, facebookLoginErrorReads)
}

object FacebookClient {
  def apply(clientId: FacebookClientId, appSecret: FacebookAppSecret): FacebookClient =
    new FacebookClient(clientId, appSecret)(uriService = URIService(clientId, appSecret))

  def apply(): FacebookClient =
    new FacebookClient(clientId, appSecret)

  def apply(asyncRequestService: AsyncRequestService): FacebookClient =
    new FacebookClient(clientId, appSecret)(asyncRequestService)

  def loginErrorFE(message: String) = Future.successful(FacebookTokenError(FacebookError(message)).asLeft)

  type FacebookAccessTokenResult = Future[Either[FacebookTokenError, FacebookAccessToken]]
}

