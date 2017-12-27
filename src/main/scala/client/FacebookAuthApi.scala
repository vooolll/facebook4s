package client

import domain.oauth.FacebookCode
import services.FacebookInternals

import scala.concurrent.Future

trait FacebookAuthApi extends FacebookInternals {

  import client.FacebookClient._
  import serialization.FacebookDecoders._
  import uriService._

  /**
    * @return future application access token
    */
  def appAccessToken(): Future[AccessToken] = sendRequestOrFail(appTokenUri)(decodeAppAccessToken)

  /**
    * @param code client code
    * @param machineId optional value that helps to identify specified client
    * @return future long lived user access token
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def userAccessToken(code: String, machineId: Option[String] = None): Future[AccessToken] =
    sendRequestOrFail(userTokenUri(code, machineId))(decodeUserAccessToken)

  /**
    * @param longLivedTokenValue long lived token
    * @return future client code
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def clientCode(longLivedTokenValue: String): Future[ClientCode] = {
    sendRequestOrFail(accessTokenCodeUri(longLivedTokenValue))(decodeClientCode)
  }

  /**
    * @param shortLivedTokenValue short lived token
    * @return future long lived user access token
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def extendUserAccessToken(shortLivedTokenValue: String): Future[AccessToken] =
    sendRequestOrFail(longLivedTokenUri(shortLivedTokenValue))(decodeUserAccessToken)

  /**
    * @return Either future value of facebook app access token or FacebookOauthError
    */
  def appAccessTokenResult(): AsyncAccessTokenResult = sendRequest(appTokenUri)(decodeAppAccessToken)

  /**
    * @param longLivedTokenValue long lived user access token value
    * @return Either future value of facebook client code or FacebookOauthError
    */
  def clientCodeResult(longLivedTokenValue: String): AsyncClientCodeResult = {
    sendRequest(accessTokenCodeUri(longLivedTokenValue))(decodeClientCode)
  }

  /**
    * @param code client code
    * @param machineId optional value that helps to identify specified client
    * @return Either future long lived user access token or FacebookOauthError
    */
  def userAccessTokenResult(code: String, machineId: Option[String]): AsyncAccessTokenResult =
    sendRequest(userTokenUri(code, machineId))(decodeUserAccessToken)

  /**
    * @param shortLivedTokenValue short lived user access token
    * @return Either future long lived user access token or FacebookOauthError
    */
  def extendUserAccessTokenResult(shortLivedTokenValue: String): AsyncAccessTokenResult =
    sendRequest(longLivedTokenUri(shortLivedTokenValue))(decodeUserAccessToken)

  /**
    *
    * @param permissions permissions you require for your application
    * @param responseType Determines whether the response data included when the redirect back to the app occurs is in
    *                     URL parameters or fragments. Could be (code, token, code and token or granted_scopes)
    * @param state An arbitrary unique string created by your app to guard against Cross-site Request Forgery
    * @return url that can be used by user of your app log in facebook
    */
  def authUrl(permissions  : Seq[Permissions],
              responseType : ResponseType = FacebookCode,
              state        : Option[String] = None): String =
    uriService.authUrl(permissions, responseType, state).toString()

}