package client

import config.FacebookConfig._
import domain.feed._
import domain.likes.FacebookLikes
import domain.oauth._
import domain.permission.FacebookPermissions._
import domain.posts._
import domain.profile._
import services._

import scala.concurrent._

/**
  * Facebook client, api should be used via this object, it provides api methods for your application
  * @param clientId your application id
  * @param appSecret your application secret
  */
class FacebookClient(val clientId: FacebookClientId, val appSecret: FacebookAppSecret)
  extends FacebookInternals {

  import client.FacebookClient._
  import serialization.FacebookDecoders._
  import FacebookUserAttribute._
  import FacebookPostAttributes._
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
    * @param userId Facebook user id
    * @param accessToken User access token
    * @param fields Sequence of facebook post attributes
    * @return Facebook user feed
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def feed(userId: UserId, accessToken: AccessToken, fields: Seq[FacebookPostAttribute]): Future[UserFeed] =
    sendRequestOrFail(userFeedUri(accessToken, userId, fields))(decodeFeed)

  /**
    * @param userId Facebook user id
    * @param accessToken User access token
    * @return Facebook user feed
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def feed(userId: UserId, accessToken: AccessToken): Future[UserFeed] =
    feed(userId, accessToken, defaultPostAttributeValues)

  /**
    * @param userId Facebook user id
    * @param accessTokenValue Facebook user access token string value
    * @param fields Sequence of facebook post attributes
    * @return Facebook user feed
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def feed(userId: UserId, accessTokenValue: String, fields: Seq[FacebookPostAttribute]): Future[UserFeed] =
    feed(userId, accessToken(accessTokenValue), fields)

  /**
    * @param userId Facebook user id
    * @param accessTokenValue Facebook user access token string value
    * @return Facebook user feed
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def feed(userId: UserId, accessTokenValue: String): Future[UserFeed] =
    feed(userId, accessTokenValue, defaultPostAttributeValues)


  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken Facebook access token
    * @param fields Sequence of facebook post attributes
    * @return Facebook post details
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def post(postId: PostId, accessToken: AccessToken, fields: Seq[FacebookPostAttribute]): Future[Post] = {
    sendRequestOrFail(postUri(postId, accessToken, fields))(decodePost)
  }

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken Facebook access token
    * @return Facebook post details
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def post(postId: PostId, accessToken: AccessToken): Future[Post] = {
    sendRequestOrFail(postUri(postId, accessToken, defaultPostAttributeValues))(decodePost)
  }

  /**
    * @param postId Facebook post id
    * @param accessTokenValue User access token value
    * @return Facebook likes
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def likes(postId: PostId, accessTokenValue: String): Future[Likes] =
    likes(postId, accessToken(accessTokenValue))


  /**
    * @param postId Facebook post id
    * @param accessToken User access token
    * @return Facebook likes
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def likes(postId: PostId, accessToken: AccessToken): Future[Likes] = ???

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
    * @param userId Facebook user id
    * @param accessToken Facebook user access token
    * @param attributes Sequence of FacebookUserAttribute
    * @return Facebook user profile
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def userProfile(userId: UserId, accessToken: AccessToken, attributes: Seq[Attributes]): Future[User] =
    sendRequestOrFail(userUri(accessToken, userId, attributes))(decodeUser)


  /**
    * @param userId Facebook user id
    * @param accessToken Facebook user access token
    * @return Facebook user profile
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def userProfile(userId: UserId, accessToken: AccessToken): Future[User] =
    userProfile(userId, accessToken, defaultAttributeValues)


  /**
    * @param userId Facebook user id
    * @param accessTokenValue Facebook user access token string value
    * @param attributes Sequence of FacebookUserAttribute
    * @return Facebook user profile
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def userProfile(userId: UserId, accessTokenValue: String, attributes: Seq[Attributes]): Future[User] =
    userProfile(userId, accessToken(accessTokenValue), attributes)

  /**
    * @param userId Facebook user id
    * @param accessTokenValue Facebook user access token string value
    * @return Facebook user profile
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def userProfile(userId: UserId, accessTokenValue: String): Future[User] =
    userProfile(userId, accessTokenValue, defaultAttributeValues)

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
    * @param userId Facebook user id
    * @param accessToken Facebook user access token with "user_posts" permission
    * @param fields Sequence of facebook post attributes
    * @return Either facebook user feed or error FacebookOauthError
    */
  def feedResult(userId: UserId, accessToken: AccessToken, fields: Seq[FacebookPostAttribute]): AsyncUserFeedResult =
    sendRequest(userFeedUri(accessToken, userId, fields))(decodeFeed)


  /**
    * @param userId Facebook user id
    * @param accessToken Facebook user access token with "user_posts" permission
    * @return Either facebook user feed or error FacebookOauthError
    */
  def feedResult(userId: UserId, accessToken: AccessToken): AsyncUserFeedResult =
    feedResult(userId, accessToken, defaultPostAttributeValues)

  /**
    * @param userId Facebook user id
    * @param accessTokenValue Facebook user access token with "user_posts" permission
    * @param fields Sequence of facebook post attributes
    * @return Either facebook user feed or error FacebookOauthError
    */
  def feedResult(userId: UserId, accessTokenValue: String, fields: Seq[FacebookPostAttribute]): AsyncUserFeedResult =
    feedResult(userId, accessToken(accessTokenValue), fields)

  /**
    * @param userId Facebook user id
    * @param accessTokenValue Facebook user access token with "user_posts" permission
    * @return Either facebook user feed or error FacebookOauthError
    */
  def feedResult(userId: UserId, accessTokenValue: String): AsyncUserFeedResult =
    feedResult(userId, accessTokenValue, defaultPostAttributeValues)


  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken Facebook access token
    * @param fields Sequence of facebook post attributes
    * @return Either facebook post details or error FacebookOauthError
    */
  def postResult(postId: PostId, accessToken: AccessToken, fields: Seq[FacebookPostAttribute]): AsyncPostResult = {
    sendRequest(postUri(postId, accessToken, fields))(decodePost)
  }

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken Facebook access token
    * @return Either facebook post details or error FacebookOauthError
    */
  def postResult(postId: PostId, accessToken: AccessToken): AsyncPostResult = {
    sendRequest(postUri(postId, accessToken, defaultPostAttributeValues))(decodePost)
  }

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessTokenValue User access token value
    * @return Either facebook post details or error FacebookOauthError
    */
  def likesResult(postId: PostId, accessTokenValue: String): AsyncLikesResult =
    likesResult(postId, accessToken(accessTokenValue))

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken User access token
    * @return Either facebook post details or error FacebookOauthError
    */
  def likesResult(postId: PostId, accessToken: AccessToken): AsyncLikesResult = ???

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

  /**
    * @param userId FacebookUserId
    * @param accessToken Facebook user access token
    * @param attributes Sequence of FacebookUserAttribute
    * @return Facebook user profile or error FacebookOauthError
    */
  def userProfileResult(userId: UserId, accessToken: AccessToken, attributes: Seq[Attributes]): AsyncUserResult =
    sendRequest(userUri(accessToken, userId, attributes))(decodeUser)

  /**
    * @param userId FacebookUserId
    * @param accessToken Facebook user access token
    * @return Facebook user profile or error FacebookOauthError
    */
  def userProfileResult(userId: UserId, accessToken: AccessToken): AsyncUserResult =
    userProfileResult(userId, accessToken, defaultAttributeValues)

  /**
    * @param userId FacebookUserId
    * @param accessTokenValue Facebook user access token string value
    * @param attributes Sequence of FacebookUserAttribute
    * @return Facebook user profile or error FacebookOauthError
    */
  def userProfileResult(userId: UserId, accessTokenValue: String,
                        attributes: Seq[Attributes]): AsyncUserResult =
    userProfileResult(userId, accessToken(accessTokenValue), attributes)

  /**
    * @param userId FacebookUserId
    * @param accessTokenValue Facebook user access token string value
    * @return Facebook user profile or error FacebookOauthError
    */
  def userProfileResult(userId: UserId, accessTokenValue: String): AsyncUserResult =
    userProfileResult(userId, accessTokenValue, defaultAttributeValues)

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
  type UserFeed = FacebookFeed
  type UserId = FacebookUserId
  type User = FacebookUser
  type Permissions = FacebookPermission
  type ResponseType = FacebookOauthResponseType
  type ApplicationId = FacebookApplicationId
  type Attributes = FacebookUserAttribute
  type PostId = FacebookPostId
  type Post = FacebookPost
  type Likes = FacebookLikes

  type AsyncUserFeedResult = Future[Either[ApiError, UserFeed]]
  type AsyncAccessTokenResult = Future[Either[ApiError, AccessToken]]
  type AsyncClientCodeResult = Future[Either[ApiError, ClientCode]]
  type AsyncApplicationResult = Future[Either[ApiError, Application]]
  type AsyncUserResult = Future[Either[ApiError, User]]
  type AsyncPostResult = Future[Either[ApiError, Post]]
  type AsyncLikesResult = Future[Either[ApiError, Post]]
}

