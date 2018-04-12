package client

import client.FacebookClient.{AccessToken, FutureResult, TaggableFriends, UserId}
import domain.friends.FacebookTaggableFriends
import services.FacebookInternals

import scala.concurrent.Future

trait FacebookFriendsApi extends FacebookInternals {

  import serialization.FacebookDecoders.decodeTaggableFriend

  /**
    * Returns taggable_friends for given user id
    * @param userId facebook user id
    * @param accessTokenValue facebook access token value
    */
  def taggableFriends(userId: UserId, accessTokenValue: String): Future[TaggableFriends]

  /**
    * Returns taggable_friends for given user id
    * @param userId facebook user id
    * @param accessToken facebook access token
    */
  def taggableFriends(userId: UserId, accessToken: AccessToken): Future[TaggableFriends]

  /**
    * Returns taggable_friends for given user id
    * @param userId facebook user id
    * @param accessToken facebook access token
    */
  def taggableFriendsResult(userId: UserId, accessToken: AccessToken): FutureResult[TaggableFriends]

  /**
    * Returns taggable_friends for given user id
    * @param userId facebook user id
    * @param accessTokenValue facebook access token value
    */
  def taggableFriendsResult(userId: UserId, accessTokenValue: String): FutureResult[TaggableFriends]
}
