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
  import domainParseService._
  import uriService._

  def appAccessToken(): Future[AccessToken] = appAccessTokenEither() map valueOrException

  def userAccessToken(code: String, machineId: Option[String] = None): Future[AccessToken] =
    userAccessTokenEither(code, machineId) map valueOrException

  def clientCode(longLivedTokenValue: String): Future[ClientCode] = {
    clientCodeEither(longLivedTokenValue) map valueOrException
  }

  def extendUserAccessToken(shortLivedToken: String): Future[AccessToken] =
    extendUserAccessTokenEither(shortLivedToken) map valueOrException

  def appAccessTokenEither(): AsyncAccessTokenResult = sendRequest(appTokenUri)(facebookAppAccessTokenReads)

  def clientCodeEither(longLivedTokenValue: String): AsyncClientCodeResult = {
    println("alive")
    sendRequest(accessTokenCodeUri(longLivedTokenValue))(facebookClientCodeReads)
  }

  def userAccessTokenEither(code: String, machineId: Option[String]): AsyncAccessTokenResult =
    sendRequest(userTokenUri(code, machineId))(facebookUserAccessTokenReads)

  def extendUserAccessTokenEither(shortLivedTokenValue: String): AsyncAccessTokenResult =
    sendRequest(longLivedTokenUri(shortLivedTokenValue))(facebookUserAccessTokenReads)


  private def sendRequest[A](uri: URLBuilder)(reads: Reads[A]) = {
    sendAndParseTo(uri)(reads, facebookLoginErrorReads)(loginErrorFE)
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

