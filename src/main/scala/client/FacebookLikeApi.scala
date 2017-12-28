package client

import client.FacebookClient._
import domain.likes.FacebookLikes
import domain.posts.FacebookPostId
import services.FacebookInternals

import scala.concurrent.Future

trait FacebookLikeApi extends FacebookInternals {

  import serialization.FacebookDecoders.decodeLikes
  import uriService._

  type Likes = FacebookLikes
  type AsyncLikesResult = Future[Either[ApiError, Likes]]

  /**
    * @param postId Facebook post id
    * @param accessTokenValue User access token value
    * @return Facebook likes
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def likes(postId: FacebookPostId, accessTokenValue: String): Future[Likes] =
    likes(postId, accessToken(accessTokenValue), summary = false)

  /**
    * @param postId Facebook post id
    * @param accessTokenValue User access token value
    * @param summary Boolean flag, retrieve summary or not
    * @return Facebook likes
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def likes(postId: FacebookPostId, accessTokenValue: String, summary: Boolean): Future[Likes] =
    likes(postId, accessToken(accessTokenValue), summary)

  /**
    * @param postId Facebook post id
    * @param accessToken User access token
    * @param summary Boolean flag, retrieve summary or not
    * @return Facebook likes
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def likes(postId: FacebookPostId, accessToken: AccessToken, summary: Boolean): Future[Likes] =
    sendRequestOrFail(likesUri(postId, accessToken, summary))

  /**
    * @param postId Facebook post id
    * @param accessToken User access token
    * @return Facebook likes
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def likes(postId: FacebookPostId, accessToken: AccessToken): Future[Likes] =
    likes(postId, accessToken, summary = false)

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessTokenValue User access token value
    * @param summary Boolean flag, retrieve summary or not
    * @return Either facebook post details or error FacebookOauthError
    */
  def likesResult(postId: FacebookPostId, accessTokenValue: String, summary: Boolean): AsyncLikesResult =
    likesResult(postId, accessToken(accessTokenValue), summary)

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessTokenValue User access token value
    * @return Either facebook post details or error FacebookOauthError
    */
  def likesResult(postId: FacebookPostId, accessTokenValue: String): AsyncLikesResult =
    likesResult(postId, accessToken(accessTokenValue), summary = false)

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken User access token
    * @param summary Boolean flag, retrieve summary or not
    * @return Either facebook post details or error FacebookOauthError
    */
  def likesResult(postId: FacebookPostId, accessToken: AccessToken, summary: Boolean): AsyncLikesResult =
    sendRequest(likesUri(postId, accessToken, summary))

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken User access token
    * @return Either facebook post details or error FacebookOauthError
    */
  def likesResult(postId: FacebookPostId, accessToken: AccessToken): AsyncLikesResult =
    likesResult(postId, accessToken, summary = false)

}
