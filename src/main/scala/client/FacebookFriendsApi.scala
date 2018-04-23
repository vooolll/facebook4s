package client

import client.FacebookClient._
import services.FacebookInternals

import scala.concurrent.Future

trait FacebookFriendsApi extends FacebookInternals {

  import serialization.FacebookDecoders.decodeUser

  /**
    * Returns taggable_friends for given user id
    * @param userId facebook user id
    * @param accessTokenValue facebook access token value
    */
  def taggableFriends(userId: UserId, accessTokenValue: String): Future[List[User]]

  /**
    * Returns taggable_friends for given user id
    * @param userId facebook user id
    * @param accessToken facebook access token
    */
  def taggableFriends(userId: UserId, accessToken: AccessToken): Future[List[User]]

  /**
    * Returns taggable_friends for given user id
    * @param userId facebook user id
    * @param accessToken facebook access token
    */
  def taggableFriendsResult(userId: UserId, accessToken: AccessToken): FutureResult[List[User]]

  /**
    * Returns taggable_friends for given user id
    * @param userId facebook user id
    * @param accessTokenValue facebook access token value
    */
  def taggableFriendsResult(userId: UserId, accessTokenValue: String): FutureResult[List[User]]
}
