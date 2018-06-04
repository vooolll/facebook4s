package com.github.vooolll.client

import com.github.vooolll.client.FacebookClient._
import com.github.vooolll.domain.posts.FacebookPostAttributes._
import com.github.vooolll.services.FacebookInternals

import scala.concurrent.Future

trait FacebookFeedApi extends FacebookInternals {

  import com.github.vooolll.serialization.FacebookDecoders.decodeFeed

  /**
    * @param userId Facebook user id
    * @param accessToken User access token
    * @param fields Sequence of facebook post attributes
    * @return Facebook user feed
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def feed(userId: UserId, accessToken: AccessToken, fields: Seq[PostAttribute]): Future[UserFeed] =
    sendRequestOrFail(userFeedUri(accessToken, userId, fields))

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
  def feed(userId: UserId, accessTokenValue: String, fields: Seq[PostAttribute]): Future[UserFeed] =
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
    * @param userId Facebook user id
    * @param accessToken Facebook user access token with "user_posts" permission
    * @param fields Sequence of facebook post attributes
    * @return Either facebook user feed or error FacebookError
    */
  def feedResult(userId: UserId, accessToken: AccessToken, fields: Seq[PostAttribute]): FutureResult[UserFeed] =
    sendRequest(userFeedUri(accessToken, userId, fields))

  /**
    * @param userId Facebook user id
    * @param accessToken Facebook user access token with "user_posts" permission
    * @return Either facebook user feed or error FacebookError
    */
  def feedResult(userId: UserId, accessToken: AccessToken): FutureResult[UserFeed] =
    feedResult(userId, accessToken, defaultPostAttributeValues)

  /**
    * @param userId Facebook user id
    * @param accessTokenValue Facebook user access token with "user_posts" permission
    * @param fields Sequence of facebook post attributes
    * @return Either facebook user feed or error FacebookError
    */
  def feedResult(userId: UserId, accessTokenValue: String, fields: Seq[PostAttribute]): FutureResult[UserFeed] =
    feedResult(userId, accessToken(accessTokenValue), fields)

  /**
    * @param userId Facebook user id
    * @param accessTokenValue Facebook user access token with "user_posts" permission
    * @return Either facebook user feed or error FacebookError
    */
  def feedResult(userId: UserId, accessTokenValue: String): FutureResult[UserFeed] =
    feedResult(userId, accessTokenValue, defaultPostAttributeValues)

}
