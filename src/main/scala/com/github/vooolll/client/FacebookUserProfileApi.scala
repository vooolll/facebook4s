package com.github.vooolll.client

import com.github.vooolll.client.FacebookClient.{UserId, _}
import com.github.vooolll.domain.profile.FacebookUserAttribute._
import com.github.vooolll.services.FacebookInternals

import scala.concurrent.Future

trait FacebookUserProfileApi extends FacebookInternals {

  import com.github.vooolll.serialization.FacebookDecoders.decodeUser

  /**
    * @param userId Facebook user id
    * @param accessToken Facebook user access token
    * @param attributes Set of FacebookUserAttribute
    * @return Facebook user profile
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def userProfile(userId: UserId, attributes: Set[_ <: UserAttributes])(
    implicit accessToken: AccessToken
  ): Future[User] =
    sendRequestOrFail(userUri(accessToken, userId, attributes))

  /**
    * @param userId Facebook user id
    * @param accessToken Facebook user access token
    * @return Facebook user profile
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def userProfile(
    userId: UserId
  )(implicit accessToken: AccessToken): Future[User] =
    userProfile(userId, defaultAttributeValues)

  /**
    * @param accessToken Facebook user access token
    * @return Facebook user profile
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def currentUserProfile(implicit accessToken: AccessToken): Future[User] =
    userProfile(new UserId("me"), defaultAttributeValues)

  /**
    * @param userId FacebookUserId
    * @param accessToken Facebook user access token
    * @param attributes Set of FacebookUserAttribute
    * @return Facebook user profile or error FacebookError
    */
  def userProfileResult(userId: UserId, attributes: Set[_ <: UserAttributes])(
    implicit accessToken: AccessToken
  ): FutureResult[User] =
    sendRequest(userUri(accessToken, userId, attributes))

  /**
    * @param userId FacebookUserId
    * @param accessToken Facebook user access token
    * @return Facebook user profile or error FacebookError
    */
  def userProfileResult(
    userId: UserId
  )(implicit accessToken: AccessToken): FutureResult[User] =
    userProfileResult(userId, defaultAttributeValues)

}
