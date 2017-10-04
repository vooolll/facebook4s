package api

import akka.http.scaladsl.model._
import config.FacebookConfig._
import domain._
import services._
import cats.syntax.either._
import cats.syntax.option._
import play.api.libs.json.Reads

import scala.concurrent._

class FacebookClient(val clientId: FacebookClientId, val appSecret: FacebookAppSecret)
  extends FacebookInternals {

  import api.FacebookJsonSerializers._
  import api.FacebookClient._

  import asyncRequestService._
  import transformer._
  import uriService._

  def appAccessToken(): Future[FacebookAccessToken] = appAccessTokenEither() map valueOrException

  def userAccessToken(code: String): Future[FacebookAccessToken] =
    userAccessTokenEither(code) map valueOrException

  def appAccessTokenEither(): FutureFacebookAccessTokenResult =
    obtainAccessToken()(facebookAppAccessTokenReads)

  def userAccessTokenEither(code: String): FutureFacebookAccessTokenResult =
    obtainAccessToken(code.some)(facebookUserAccessTokenReads)

  def extendUserAccessTokenEither(shortLivedToken: String): FutureFacebookAccessTokenResult =
    extendAccessToken(shortLivedToken)(facebookUserAccessTokenReads)

  def extendUserAccessToken(shortLivedToken: String): Future[FacebookAccessToken] =
    extendUserAccessTokenEither(shortLivedToken) map valueOrException

  private def obtainAccessToken(code: Option[String] = None)
                               (tokenReads: Reads[FacebookAccessToken]): FutureFacebookAccessTokenResult =
    for {
      response <- sendRequest(tokenUri(code))
      userAccessToken <- parseToJson(response)(tokenReads)
    } yield userAccessToken

  private def extendAccessToken(shortLivedToken: String)
                                (tokenReads: Reads[FacebookAccessToken]): FutureFacebookAccessTokenResult =
    for {
      response <- sendRequest(longLivedTokenUri(shortLivedToken))
      userAccessToken <- parseToJson(response)(tokenReads)
    } yield userAccessToken

  private def parseToJson(response: HttpResponse)
                         (tokenReads: Reads[FacebookAccessToken]) = parseResponse(response)(loginErrorFE)(
    tokenReads, facebookLoginErrorReads)
}

object FacebookClient {
  def apply(clientId: FacebookClientId, appSecret: FacebookAppSecret): FacebookClient =
    new FacebookClient(clientId, appSecret)

  def apply(): FacebookClient =
    new FacebookClient(clientId, appSecret)

  def loginErrorFE(message: String) = Future.successful(FacebookTokenError(FacebookError(message)).asLeft)

  type FutureFacebookAccessTokenResult = Future[Either[FacebookTokenError, FacebookAccessToken]]
  type FacebookAccessTokenResult = Either[FacebookTokenError, FacebookAccessToken]
}

