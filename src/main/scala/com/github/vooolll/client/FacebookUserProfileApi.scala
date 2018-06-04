package com.github.vooolll.client

import com.github.vooolll.client.FacebookClient._
import com.github.vooolll.domain.profile.FacebookUserAttribute._
import com.github.vooolll.services.FacebookInternals

import scala.concurrent.Future

trait FacebookUserProfileApi extends FacebookInternals {

  import com.github.vooolll.serialization.FacebookDecoders.decodeUser

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
    * @return Facebook user profile or error FacebookError
    */
  def userProfileResult(userId: UserId, accessToken: AccessToken, attributes: Seq[UserAttributes]): FutureResult[User] =
    sendRequest(userUri(accessToken, userId, attributes))

  /**
    * @param userId FacebookUserId
    * @param accessToken Facebook user access token
    * @return Facebook user profile or error FacebookError
    */
  def userProfileResult(userId: UserId, accessToken: AccessToken): FutureResult[User] =
    userProfileResult(userId, accessToken, defaultAttributeValues)

  /**
    * @param userId FacebookUserId
    * @param accessTokenValue Facebook user access token string value
    * @param attributes Sequence of FacebookUserAttribute
    * @return Facebook user profile or error FacebookError
    */
  def userProfileResult(userId: UserId, accessTokenValue: String, attributes: Seq[UserAttributes]): FutureResult[User] =
    userProfileResult(userId, accessToken(accessTokenValue), attributes)

  /**
    * @param userId FacebookUserId
    * @param accessTokenValue Facebook user access token string value
    * @return Facebook user profile or error FacebookError
    */
  def userProfileResult(userId: UserId, accessTokenValue: String): FutureResult[User] =
    userProfileResult(userId, accessTokenValue, defaultAttributeValues)

}
