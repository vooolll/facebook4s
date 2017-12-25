package client

import client.FacebookClient._
import services.FacebookInternals

import scala.concurrent.Future

trait FacebookApplicationApi extends FacebookInternals {

  import serialization.FacebookDecoders._
  import uriService._

  /**
    * @param applicationId Facebook application(client) id
    * @param accessToken Facebook user access token
    * @return Facebook application details
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def application(applicationId: ApplicationId, accessToken: AccessToken): Future[Application] =
    sendRequestOrFail(applicationUri(accessToken, applicationId))(decodeApplication)

  /**
    * @param applicationId Facebook application(client) id
    * @param accessTokenValue Facebook user access token string value
    * @return Facebook application details
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def application(applicationId: ApplicationId, accessTokenValue: String): Future[Application] =
    application(applicationId, accessToken(accessTokenValue))

  /**
    * @param applicationId Facebook application(client) id
    * @param accessToken Facebook user access token
    * @return Either facebook application details or error FacebookOauthError
    */
  def applicationResult(applicationId: ApplicationId, accessToken: AccessToken): AsyncApplicationResult =
    sendRequest(applicationUri(accessToken, applicationId))(decodeApplication)

  /**
    * @param applicationId Facebook application(client) id
    * @param accessTokenValue Facebook user access token string value
    * @return Either facebook application details or error FacebookOauthError
    */
  def applicationResult(applicationId: ApplicationId, accessTokenValue: String): AsyncApplicationResult =
    applicationResult(applicationId, accessToken(accessTokenValue))
}
