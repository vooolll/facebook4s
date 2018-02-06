package client

import client.FacebookClient._
import domain.profile.FacebookUserAttribute._
import domain.profile._
import services.FacebookInternals

import scala.concurrent.Future

trait FacebookUserProfileApi extends FacebookInternals {

  import serialization.FacebookDecoders.decodeUser

  /**
    * @param userId Facebook user id
    * @param accessToken Facebook user access token
    * @param attributes Sequence of FacebookUserAttribute
    * @return Facebook user profile
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def userProfile(userId: UserId, accessToken: AccessToken, attributes: Seq[UserAttributes]): Future[User] =
    sendRequestOrFail(userUri(accessToken, userId, attributes))


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
  def userProfile(userId: UserId, accessTokenValue: String, attributes: Seq[UserAttributes]): Future[User] =
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
    * @param userId FacebookUserId
    * @param accessToken Facebook user access token
    * @param attributes Sequence of FacebookUserAttribute
    * @return Facebook user profile or error FacebookOauthError
    */
  def userProfileResult(userId: UserId, accessToken: AccessToken, attributes: Seq[UserAttributes]): AsyncUserResult =
    sendRequest(userUri(accessToken, userId, attributes))

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
                        attributes: Seq[UserAttributes]): AsyncUserResult =
    userProfileResult(userId, accessToken(accessTokenValue), attributes)

  /**
    * @param userId FacebookUserId
    * @param accessTokenValue Facebook user access token string value
    * @return Facebook user profile or error FacebookOauthError
    */
  def userProfileResult(userId: UserId, accessTokenValue: String): AsyncUserResult =
    userProfileResult(userId, accessTokenValue, defaultAttributeValues)

}
