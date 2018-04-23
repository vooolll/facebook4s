package client

import client.FacebookClient.{accessToken, _}
import domain.profile.FacebookUserAttribute
import services.FacebookInternals

import scala.concurrent.Future

trait FacebookFriendsApi extends FacebookInternals {

  import serialization.FacebookDecoders.decodeFriends

  /**
    * Returns friends that installed current app for given user id
    * @param userId facebook user id
    * @param accessTokenValue facebook access token value
    */
  def friends(userId: UserId, accessTokenValue: String): Future[Friends] =
    friends(userId, accessToken(accessTokenValue))

  /**
    * Returns friends that installed current app for given user id
    * @param userId facebook user id
    * @param accessToken facebook access token
    */
  def friends(userId: UserId, accessToken: AccessToken): Future[Friends] =
    sendRequestOrFail(friendsUri(accessToken, userId, FacebookUserAttribute.defaultAttributeValues))

  /**
    * Returns friends that installed current app for given user id
    * @param userId facebook user id
    * @param accessToken facebook access token
    */
  def friendsResult(userId: UserId, accessToken: AccessToken): FutureResult[Friends] =
    sendRequest(friendsUri(accessToken, userId, FacebookUserAttribute.defaultAttributeValues))

  /**
    * Returns friends that installed current app for given user id
    * @param userId facebook user id
    * @param accessTokenValue facebook access token value
    */
  def friendsResult(userId: UserId, accessTokenValue: String): FutureResult[Friends] =
    friendsResult(userId, accessToken(accessTokenValue))

}
