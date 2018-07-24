package com.github.vooolll.client

import com.github.vooolll.client.FacebookClient.{accessToken, _}
import com.github.vooolll.domain.profile.FacebookUserAttribute
import com.github.vooolll.services.FacebookInternals

import scala.concurrent.Future

trait FacebookFriendsApi extends FacebookInternals {

  import com.github.vooolll.serialization.FacebookDecoders.decodeFriends

  /**
    * Returns friends that installed current app for given user id
    * @param userId facebook user id
    * @param accessToken facebook access token
    */
  def friends(userId: UserId)(implicit accessToken: AccessToken): Future[Friends] =
    sendRequestOrFail(friendsUri(accessToken, userId, FacebookUserAttribute.defaultAttributeValues))

  /**
    * Returns friends that installed current app for given user id
    * @param userId facebook user id
    * @param accessToken facebook access token
    */
  def friendsResult(userId: UserId)(implicit accessToken: AccessToken): FutureResult[Friends] =
    sendRequest(friendsUri(accessToken, userId, FacebookUserAttribute.defaultAttributeValues))

}
