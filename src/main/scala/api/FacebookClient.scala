package api

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
  import domainParseService._
  import uriService._

  def appAccessToken(): Future[AccessToken] = sendRequestOrFail(appTokenUri)(facebookAppAccessTokenReads)

  def userAccessToken(code: String, machineId: Option[String] = None): Future[AccessToken] =
    sendRequestOrFail(userTokenUri(code, machineId))(facebookUserAccessTokenReads)

  def clientCode(longLivedTokenValue: String): Future[ClientCode] = {
    sendRequestOrFail(accessTokenCodeUri(longLivedTokenValue))(facebookClientCodeReads)
  }

  def extendUserAccessToken(shortLivedTokenValue: String): Future[AccessToken] =
    sendRequestOrFail(longLivedTokenUri(shortLivedTokenValue))(facebookUserAccessTokenReads)

  def appAccessTokenEither(): AsyncAccessTokenResult = sendRequest(appTokenUri)(facebookAppAccessTokenReads)

  def clientCodeEither(longLivedTokenValue: String): AsyncClientCodeResult = {
    sendRequest(accessTokenCodeUri(longLivedTokenValue))(facebookClientCodeReads)
  }

  def userAccessTokenEither(code: String, machineId: Option[String]): AsyncAccessTokenResult =
    sendRequest(userTokenUri(code, machineId))(facebookUserAccessTokenReads)

  def extendUserAccessTokenEither(shortLivedTokenValue: String): AsyncAccessTokenResult =
    sendRequest(longLivedTokenUri(shortLivedTokenValue))(facebookUserAccessTokenReads)


  private def sendRequest[A](uri: URLBuilder)(reads: Reads[A]) = {
    send(uri)(reads, facebookLoginErrorReads)(loginErrorFE)(appResources)
  }

  private def sendRequestOrFail[A](uri: URLBuilder)(reads: Reads[A]) = {
    sendOrFail(uri)(reads, facebookLoginErrorReads)(loginErrorFE)(appResources)
  }

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

