package api

import akka.http.scaladsl.model._

import config.FacebookConfig._
import domain._
import services._

import cats.syntax.either._
import cats.syntax.option._

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

  def appAccessTokenEither(): FutureFacebookAccessTokenResult = obtainAccessToken()

  def userAccessTokenEither(code: String): FutureFacebookAccessTokenResult = obtainAccessToken(code.some)

  private def obtainAccessToken(code: Option[String] = None): FutureFacebookAccessTokenResult = for {
    response <- sendRequest(tokenUri(code))
    userAccessToken <- parseToJson(response)
  } yield userAccessToken

  private def parseToJson(response: HttpResponse) = parseResponse(response)(loginErrorFE)(
    facebookAccessTokenReads, facebookLoginErrorReads)
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

