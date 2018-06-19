package com.github.vooolll.client

import com.github.vooolll.client.FacebookClient._
import com.github.vooolll.domain.comments.FacebookCommentAttributes._
import com.github.vooolll.domain.comments.FacebookCommentsAttributes._
import com.github.vooolll.services.FacebookInternals

import scala.concurrent.Future

trait FacebookCommentApi extends FacebookInternals {
  import com.github.vooolll.serialization.FacebookDecoders.{decodeComments, decodeComment}

  /**
    * @param commentId Facebook comment id
    * @param accessTokenValue User access token value
    * @return Facebook comment
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  @deprecated("use `AccessToken` instead of String", "0.2.9")
  def comment(commentId: CommentId, accessTokenValue: String, attributes: Seq[CommentAttribute]): Future[Comment] =
    comment(commentId, attributes)(accessToken(accessTokenValue))

  /**
    * @param commentId Facebook comment id
    * @param accessTokenValue User access token value
    * @return Facebook comment
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  @deprecated("use `AccessToken` instead of String", "0.2.9")
  def comment(commentId: CommentId, accessTokenValue: String): Future[Comment] =
    comment(commentId, defaultCommentAttributeValues)(accessToken(accessTokenValue))

  /**
    * @param commentId Facebook comment id
    * @param accessToken User access token value
    * @return Facebook comment
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def comment(commentId: CommentId, attributes: Seq[CommentAttribute])(implicit accessToken: AccessToken): Future[Comment] =
    sendRequestOrFail(commentUri(commentId, accessToken, attributes))(decodeComment)

  /**
    * @param commentId Facebook comment id
    * @param accessToken User access token value
    * @return Facebook comment
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def comment(commentId: CommentId)(implicit accessToken: AccessToken): Future[Comment] =
    comment(commentId, defaultCommentAttributeValues)

  /**
    * @param commentId Facebook comment id
    * @param accessToken User access token value
    * @return Either facebook comment or error FacebookError
    */
  def commentResult(commentId: CommentId, attributes: Seq[CommentAttribute])(implicit accessToken: AccessToken): FutureResult[Comment] =
    sendRequest(commentUri(commentId, accessToken, attributes))(decodeComment)

  /**
    * @param commentId Facebook comment id
    * @param accessTokenValue User access token value
    * @return Either facebook comment or error FacebookError
    */
  @deprecated("use `AccessToken` instead of String", "0.2.9")
  def commentResult(commentId: CommentId, accessTokenValue: String, attributes: Seq[CommentAttribute]): FutureResult[Comment] =
    commentResult(commentId, attributes)(accessToken(accessTokenValue))

  /**
    * @param commentId Facebook comment id
    * @param accessTokenValue User access token value
    * @return Either facebook comment or error FacebookError
    */
  @deprecated("use `AccessToken` instead of String", "0.2.9")
  def commentResult(commentId: CommentId, accessTokenValue: String): FutureResult[Comment] =
    commentResult(commentId, defaultCommentAttributeValues)(accessToken(accessTokenValue))

  /**
    * @param commentId Facebook comment id
    * @param accessToken User access token value
    * @return Either facebook comment or error FacebookError
    */
  def commentResult(commentId: CommentId)(implicit accessToken: AccessToken): FutureResult[Comment] =
    commentResult(commentId, defaultCommentAttributeValues)

  /**
    * @param postId Facebook post id
    * @param accessTokenValue User access token value
    * @return Facebook comments
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  @deprecated("use `AccessToken` instead of String", "0.2.9")
  def comments(postId: PostId, accessTokenValue: String): Future[Comments] =
    comments(postId, summary = false)(accessToken(accessTokenValue))

  /**
    * @param postId Facebook post id
    * @param accessTokenValue User access token value
    * @param summary Boolean flag, retrieve summary or not
    * @return Facebook comments
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  @deprecated("use `AccessToken` instead of String", "0.2.9")
  def comments(postId: PostId, accessTokenValue: String, summary: Boolean): Future[Comments] =
    comments(postId, summary)(accessToken(accessTokenValue))

  /**
    * @param postId Facebook post id
    * @param accessToken User access token
    * @param summary Boolean flag, retrieve summary or not
    * @return Facebook comments
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def comments(postId: PostId, summary: Boolean)(implicit accessToken: AccessToken): Future[Comments] =
    sendRequestOrFail(commentsUri(postId, accessToken, defaultCommentsAttributeValues, summary))(decodeComments)

  /**
    * @param postId Facebook post id
    * @param accessToken User access token
    * @return Facebook comments
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def comments(postId: PostId)(implicit accessToken: AccessToken): Future[Comments] =
    comments(postId, summary = false)

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessTokenValue User access token value
    * @param summary Boolean flag, retrieve summary or not
    * @return Either facebook comments or error FacebookError
    */
  @deprecated("use `AccessToken` instead of String", "0.2.9")
  def commentsResult(postId: PostId, accessTokenValue: String, summary: Boolean): FutureResult[Comments] =
    commentsResult(postId, summary)(accessToken(accessTokenValue))

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessTokenValue User access token value
    * @return Either facebook comments or error FacebookError
    */
  @deprecated("use `AccessToken` instead of String", "0.2.9")
  def commentsResult(postId: PostId, accessTokenValue: String): FutureResult[Comments] =
    commentsResult(postId, summary = false)(accessToken(accessTokenValue))

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken User access token
    * @param summary Boolean flag, retrieve summary or not
    * @return Either facebook comments or error FacebookError
    */
  def commentsResult(postId: PostId, summary: Boolean)(implicit accessToken: AccessToken): FutureResult[Comments] =
    sendRequest(commentsUri(postId, accessToken, defaultCommentsAttributeValues, summary))(decodeComments)

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken User access token
    * @return Either facebook comments or error FacebookError
    */
  def commentsResult(postId: PostId)(implicit accessToken: AccessToken): FutureResult[Comments] =
    commentsResult(postId, summary = false)

}
