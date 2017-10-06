package api

import akka.http.scaladsl.model._
import cats.syntax.either._
import config.FacebookConfig._
import domain._
import org.f100ded.scalaurlbuilder.URLBuilder
import play.api.libs.json.Reads
import services._

import scala.concurrent._

class FacebookClient(val clientId: FacebookClientId, val appSecret: FacebookAppSecret)
  extends FacebookInternals {

  import api.FacebookClient._
  import api.FacebookJsonSerializers._
  import asyncRequestService._
  import transformer._
  import uriService._

  def appAccessToken(): Future[AccessToken] = appAccessTokenEither() map valueOrException

  def userAccessToken(code: String): Future[AccessToken] = userAccessTokenEither(code) map valueOrException

  def clientCode(longLivedTokenValue: String): Future[ClientCode] =
    clientCodeEither(longLivedTokenValue) map valueOrException

  def extendUserAccessToken(shortLivedToken: String): Future[AccessToken] =
    extendUserAccessTokenEither(shortLivedToken) map valueOrException

  def appAccessTokenEither(): AsyncAccessTokenResult = sendAndParseTo(appTokenUri)(facebookAppAccessTokenReads)

  def clientCodeEither(longLivedTokenValue: String): AsyncClientCodeResult =
    sendAndParseTo(accessTokenCodeUri(longLivedTokenValue))(facebookClientCodeReads)

  def userAccessTokenEither(code: String): AsyncAccessTokenResult =
    sendAndParseTo(userTokenUri(code))(facebookUserAccessTokenReads)

  def extendUserAccessTokenEither(shortLivedTokenValue: String): AsyncAccessTokenResult =
    extendAccessToken(shortLivedTokenValue)(facebookUserAccessTokenReads)

  private def sendAndParseTo[T](uri: URLBuilder)(tokenReads: Reads[T]) = for {
    response <- sendRequest(uri)
    userAccessToken <- parseToJson(response)(tokenReads)
  } yield userAccessToken

  private def extendAccessToken(shortLivedToken: String)
                                (tokenReads: Reads[FacebookAccessToken]): AsyncAccessTokenResult =
    for {
      response <- sendRequest(longLivedTokenUri(shortLivedToken))
      userAccessToken <- parseToJson(response)(tokenReads)
    } yield userAccessToken

  private def parseToJson[T](response: HttpResponse)
                         (tokenReads: Reads[T]) = parseResponse(response)(loginErrorFE)(
    tokenReads, facebookLoginErrorReads)
}

object FacebookClient {
  def apply(clientId: FacebookClientId, appSecret: FacebookAppSecret): FacebookClient =
    new FacebookClient(clientId, appSecret)

  def apply(): FacebookClient =
    new FacebookClient(clientId, appSecret)

  def loginErrorFE(message: String) = Future.successful(FacebookTokenError(FacebookError(message)).asLeft)

  type AccessToken = FacebookAccessToken
  type TokenError = FacebookTokenError
  type ClientCode = FacebookClientCode

  type AsyncAccessTokenResult = Future[Either[TokenError, AccessToken]]
  type AsyncClientCodeResult = Future[Either[TokenError, ClientCode]]
  type FacebookAccessTokenResult = Either[TokenError, AccessToken]
}

