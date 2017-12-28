package client

import client.FacebookClient._
import domain.profile.FacebookUserAttribute._
import domain.profile._
import services.FacebookInternals

import scala.concurrent.Future

trait FacebookUserProfileApi extends FacebookInternals {

  type User = FacebookUser
  type AsyncUserResult = Future[Either[ApiError, User]]

  import serialization.FacebookDecoders.decodeUser
  import uriService._

  /**
    * @param userId Facebook user id
    * @param accessToken Facebook user access token
    * @param attributes Sequence of FacebookUserAttribute
    * @return Facebook user profile
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def userProfile(userId: FacebookUserId, accessToken: AccessToken, attributes: Seq[Attributes]): Future[User] =
    sendRequestOrFail(userUri(accessToken, userId, attributes))


  /**
    * @param userId Facebook user id
    * @param accessToken Facebook user access token
    * @return Facebook user profile
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def userProfile(userId: FacebookUserId, accessToken: AccessToken): Future[User] =
    userProfile(userId, accessToken, defaultAttributeValues)


  /**
    * @param userId Facebook user id
    * @param accessTokenValue Facebook user access token string value
    * @param attributes Sequence of FacebookUserAttribute
    * @return Facebook user profile
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def userProfile(userId: FacebookUserId, accessTokenValue: String, attributes: Seq[Attributes]): Future[User] =
    userProfile(userId, accessToken(accessTokenValue), attributes)

  /**
    * @param userId Facebook user id
    * @param accessTokenValue Facebook user access token string value
    * @return Facebook user profile
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def userProfile(userId: FacebookUserId, accessTokenValue: String): Future[User] =
    userProfile(userId, accessTokenValue, defaultAttributeValues)

  /**
    * @param userId FacebookUserId
    * @param accessToken Facebook user access token
    * @param attributes Sequence of FacebookUserAttribute
    * @return Facebook user profile or error FacebookOauthError
    */
  def userProfileResult(userId: FacebookUserId, accessToken: AccessToken, attributes: Seq[Attributes]): AsyncUserResult =
    sendRequest(userUri(accessToken, userId, attributes))

  /**
    * @param userId FacebookUserId
    * @param accessToken Facebook user access token
    * @return Facebook user profile or error FacebookOauthError
    */
  def userProfileResult(userId: FacebookUserId, accessToken: AccessToken): AsyncUserResult =
    userProfileResult(userId, accessToken, defaultAttributeValues)

  /**
    * @param userId FacebookUserId
    * @param accessTokenValue Facebook user access token string value
    * @param attributes Sequence of FacebookUserAttribute
    * @return Facebook user profile or error FacebookOauthError
    */
  def userProfileResult(userId: FacebookUserId, accessTokenValue: String,
                        attributes: Seq[Attributes]): AsyncUserResult =
    userProfileResult(userId, accessToken(accessTokenValue), attributes)

  /**
    * @param userId FacebookUserId
    * @param accessTokenValue Facebook user access token string value
    * @return Facebook user profile or error FacebookOauthError
    */
  def userProfileResult(userId: FacebookUserId, accessTokenValue: String): AsyncUserResult =
    userProfileResult(userId, accessTokenValue, defaultAttributeValues)

}
