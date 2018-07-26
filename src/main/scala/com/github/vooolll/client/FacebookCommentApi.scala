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
    * @param accessToken User access token value
    * @return Facebook comment
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def comment(commentId: CommentId, attributes: Set[_ <: CommentAttribute])(implicit accessToken: AccessToken): Future[Comment] =
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
  def commentResult(commentId: CommentId, attributes: Set[_ <: CommentAttribute])(implicit accessToken: AccessToken): FutureResult[Comment] =
    sendRequest(commentUri(commentId, accessToken, attributes))(decodeComment)

  /**
    * @param commentId Facebook comment id
    * @param accessToken User access token value
    * @return Either facebook comment or error FacebookError
    */
  def commentResult(commentId: CommentId)(implicit accessToken: AccessToken): FutureResult[Comment] =
    commentResult(commentId, defaultCommentAttributeValues)

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
