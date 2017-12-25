package client

import config.FacebookConfig._
import domain.oauth._
import domain.permission.FacebookPermissions._
import domain.profile._

import scala.concurrent._

/**
  * Facebook client, api should be used via this object, it provides api methods for your application
  * @param clientId your application id
  * @param appSecret your application secret
  */
class FacebookClient(val clientId: FacebookClientId, val appSecret: FacebookAppSecret) extends FacebookAuthApi
  with FacebookFeedApi
  with FacebookPostApi
  with FacebookLikeApi
  with FacebookApplicationApi
  with FacebookUserProfileApi

/**
  * Facebook client constructors and helper types
  */
object FacebookClient {

  /**
    * @param clientId your application id
    * @param appSecret your application secret
    * @return backed FacebookClient
    */
  def apply(clientId: FacebookClientId, appSecret: FacebookAppSecret): FacebookClient =
    new FacebookClient(clientId, appSecret)

  /**
    * @param clientIdValue your application id value
    * @param appSecretValue your application secret value
    * @return
    */
  def apply(clientIdValue: String, appSecretValue: String): FacebookClient =
    new FacebookClient(FacebookClientId(clientIdValue), FacebookAppSecret(appSecretValue))

  /**
    * @return FacebookClient created from application id and application secret from type safe config or
    *         OS environmental variables
    */
  def apply(): FacebookClient = new FacebookClient(clientId, appSecret)

  /**
    * @param value token sting value
    * @return FacebookAccessToken
    */
  def accessToken(value: String) = FacebookAccessToken(TokenValue(value), UserAccessToken.NotSpecified)

  type AccessToken = FacebookAccessToken
  type Application = FacebookApplication
  type ApiError = FacebookOauthError
  type ClientCode = FacebookClientCode
  type Permissions = FacebookPermission
  type ResponseType = FacebookOauthResponseType
  type ApplicationId = FacebookApplicationId
  type Attributes = FacebookUserAttribute

  type AsyncAccessTokenResult = Future[Either[ApiError, AccessToken]]
  type AsyncClientCodeResult = Future[Either[ApiError, ClientCode]]
  type AsyncApplicationResult = Future[Either[ApiError, Application]]
}
