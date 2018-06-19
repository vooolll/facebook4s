package com.github.vooolll.client

import com.github.vooolll.config.FacebookConfig._
import com.github.vooolll.domain.albums.FacebookAlbums
import com.github.vooolll.domain.albums.photo.FacebookPhotoAttributes.FacebookPhotoAttribute
import com.github.vooolll.domain.albums.photo.{FacebookPhoto, FacebookPhotoId}
import com.github.vooolll.domain.comments._
import com.github.vooolll.domain.feed.FacebookFeed
import com.github.vooolll.domain.friends.FacebookFriends
import com.github.vooolll.domain.likes.FacebookLikes
import com.github.vooolll.domain.oauth._
import com.github.vooolll.domain.permission.FacebookPermissions._
import com.github.vooolll.domain.posts.FacebookPostAttributes.FacebookPostAttribute
import com.github.vooolll.domain.posts.{FacebookPost, FacebookPostId}
import com.github.vooolll.domain.profile._

import scala.concurrent._

/**
  * Facebook client, api should be used via this object, it provides api methods for your application
  * @param clientId your application id
  * @param appSecret your application secret
  */
class FacebookClient(val clientId: FacebookClientId, val appSecret: FacebookAppSecret) extends FacebookAuthApi
  with FacebookFeedApi
  with FacebookPostApi
  with FacebookLikeApi
  with FacebookApplicationApi
  with FacebookUserProfileApi
  with FacebookCommentApi
  with FacebookPhotoApi
  with FacebookAlbumApi
  with FacebookFriendsApi

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
  def accessToken(value: String) = FacebookAccessToken(TokenValue(value), UserAccessToken.notSpecified)

  type ApplicationId = FacebookApplicationId
  type UserId = FacebookUserId
  type PostId = FacebookPostId
  type PhotoId = FacebookPhotoId
  type ProfileId = FacebookProfileId
  type CommentId = FacebookCommentId

  type AccessToken = FacebookAccessToken
  type Application = FacebookApplication
  type ApiError = FacebookError
  type ClientCode = FacebookClientCode
  type Permissions = FacebookPermission
  type ResponseType = FacebookOauthResponseType

  type User = FacebookUser
  type Post = FacebookPost
  type Likes = FacebookLikes
  type UserFeed = FacebookFeed
  type Comments = FacebookComments
  type Comment = FacebookComment
  type Photo = FacebookPhoto
  type Albums = FacebookAlbums
  type Friends = FacebookFriends

  type FutureResult[T] = Future[Either[ApiError, T]]

  type PhotoAttribute = FacebookPhotoAttribute
  type PostAttribute = FacebookPostAttribute
  type UserAttributes = FacebookUserAttribute
  type CommentAttribute = FacebookCommentAttribute
}
