package client

import client.FacebookClient._
import services.FacebookInternals

import scala.concurrent.Future

trait FacebookCommentApi extends FacebookInternals {
  import serialization.FacebookDecoders.decodeComments

  /**
    * @param postId Facebook post id
    * @param accessTokenValue User access token value
    * @return Facebook comments
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def comments(postId: PostId, accessTokenValue: String): Future[Comments] =
    comments(postId, accessToken(accessTokenValue), summary = false)

  /**
    * @param postId Facebook post id
    * @param accessTokenValue User access token value
    * @param summary Boolean flag, retrieve summary or not
    * @return Facebook comments
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def comments(postId: PostId, accessTokenValue: String, summary: Boolean): Future[Comments] =
    comments(postId, accessToken(accessTokenValue), summary)

  /**
    * @param postId Facebook post id
    * @param accessToken User access token
    * @param summary Boolean flag, retrieve summary or not
    * @return Facebook comments
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def comments(postId: PostId, accessToken: AccessToken, summary: Boolean): Future[Comments] =
    sendRequestOrFail(commentsUri(postId, accessToken, summary))

  /**
    * @param postId Facebook post id
    * @param accessToken User access token
    * @return Facebook comments
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def comments(postId: PostId, accessToken: AccessToken): Future[Comments] =
    comments(postId, accessToken, summary = false)

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessTokenValue User access token value
    * @param summary Boolean flag, retrieve summary or not
    * @return Either facebook comments or error FacebookError
    */
  def commentsResult(postId: PostId, accessTokenValue: String, summary: Boolean): AsyncCommentsResult =
    commentsResult(postId, accessToken(accessTokenValue), summary)

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessTokenValue User access token value
    * @return Either facebook comments or error FacebookError
    */
  def commentsResult(postId: PostId, accessTokenValue: String): AsyncCommentsResult =
    commentsResult(postId, accessToken(accessTokenValue), summary = false)

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken User access token
    * @param summary Boolean flag, retrieve summary or not
    * @return Either facebook comments or error FacebookError
    */
  def commentsResult(postId: PostId, accessToken: AccessToken, summary: Boolean): AsyncCommentsResult =
    sendRequest(commentsUri(postId, accessToken, summary))

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken User access token
    * @return Either facebook comments or error FacebookError
    */
  def commentsResult(postId: PostId, accessToken: AccessToken): AsyncCommentsResult =
    commentsResult(postId, accessToken, summary = false)

}
