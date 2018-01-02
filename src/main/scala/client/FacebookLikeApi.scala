package client

import client.FacebookClient._
import domain.posts.FacebookPostId
import services.FacebookInternals

import scala.concurrent.Future

trait FacebookLikeApi extends FacebookInternals {

  import serialization.FacebookDecoders.decodeLikes

  /**
    * @param postId Facebook post id
    * @param accessTokenValue User access token value
    * @return Facebook likes
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def likes(postId: PostId, accessTokenValue: String): Future[Likes] =
    likes(postId, accessToken(accessTokenValue), summary = false)

  /**
    * @param postId Facebook post id
    * @param accessTokenValue User access token value
    * @param summary Boolean flag, retrieve summary or not
    * @return Facebook likes
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def likes(postId: PostId, accessTokenValue: String, summary: Boolean): Future[Likes] =
    likes(postId, accessToken(accessTokenValue), summary)

  /**
    * @param postId Facebook post id
    * @param accessToken User access token
    * @param summary Boolean flag, retrieve summary or not
    * @return Facebook likes
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def likes(postId: PostId, accessToken: AccessToken, summary: Boolean): Future[Likes] =
    sendRequestOrFail(likesUri(postId, accessToken, summary))

  /**
    * @param postId Facebook post id
    * @param accessToken User access token
    * @return Facebook likes
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def likes(postId: PostId, accessToken: AccessToken): Future[Likes] =
    likes(postId, accessToken, summary = false)

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessTokenValue User access token value
    * @param summary Boolean flag, retrieve summary or not
    * @return Either facebook post likes or error FacebookOauthError
    */
  def likesResult(postId: PostId, accessTokenValue: String, summary: Boolean): AsyncLikesResult =
    likesResult(postId, accessToken(accessTokenValue), summary)

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessTokenValue User access token value
    * @return Either facebook post likes or error FacebookOauthError
    */
  def likesResult(postId: PostId, accessTokenValue: String): AsyncLikesResult =
    likesResult(postId, accessToken(accessTokenValue), summary = false)

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken User access token
    * @param summary Boolean flag, retrieve summary or not
    * @return Either facebook post likes or error FacebookOauthError
    */
  def likesResult(postId: PostId, accessToken: AccessToken, summary: Boolean): AsyncLikesResult =
    sendRequest(likesUri(postId, accessToken, summary))

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken User access token
    * @return Either facebook post likes or error FacebookOauthError
    */
  def likesResult(postId: PostId, accessToken: AccessToken): AsyncLikesResult =
    likesResult(postId, accessToken, summary = false)

}
