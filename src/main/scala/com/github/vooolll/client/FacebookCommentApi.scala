package com.github.vooolll.client

import com.github.vooolll.client.FacebookClient._
import com.github.vooolll.domain.comments.FacebookCommentAttributes._
import com.github.vooolll.domain.comments.FacebookCommentsAttributes._
import com.github.vooolll.domain.comments.{FacebookComment, FacebookCommentId}
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
  def comment(
    commentId        : FacebookCommentId,
    accessTokenValue : String,
    attributes       : Seq[FacebookCommentAttribute]): Future[FacebookComment] =
    comment(commentId, accessToken(accessTokenValue), attributes)

  /**
    * @param commentId Facebook comment id
    * @param accessTokenValue User access token value
    * @return Facebook comment
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def comment(
    commentId        : FacebookCommentId,
    accessTokenValue : String): Future[FacebookComment] =
    comment(commentId, accessToken(accessTokenValue), defaultCommentAttributeValues)

  /**
    * @param commentId Facebook comment id
    * @param accessToken User access token value
    * @return Facebook comment
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def comment(
      commentId   : FacebookCommentId,
      accessToken : AccessToken,
      attributes  : Seq[FacebookCommentAttribute]): Future[FacebookComment] =
    sendRequestOrFail(commentUri(commentId, accessToken, attributes))(decodeComment)

  /**
    * @param commentId Facebook comment id
    * @param accessToken User access token value
    * @return Facebook comment
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def comment(
    commentId   : FacebookCommentId,
    accessToken : AccessToken): Future[FacebookComment] =
    comment(commentId, accessToken, defaultCommentAttributeValues)

  /**
    * @param commentId Facebook comment id
    * @param accessToken User access token value
    * @return Either facebook comment or error FacebookError
    */
  def commentResult(
    commentId   : FacebookCommentId,
    accessToken : AccessToken,
    attributes  : Seq[FacebookCommentAttribute]): Future[Either[ApiError, FacebookComment]] =
    sendRequest(commentUri(commentId, accessToken, attributes))(decodeComment)

  /**
    * @param commentId Facebook comment id
    * @param accessTokenValue User access token value
    * @return Either facebook comment or error FacebookError
    */
  def commentResult(
      commentId        : FacebookCommentId,
      accessTokenValue : String,
      attributes       : Seq[FacebookCommentAttribute]): Future[Either[ApiError, FacebookComment]] =
    commentResult(commentId, accessToken(accessTokenValue), attributes)

  /**
    * @param commentId Facebook comment id
    * @param accessTokenValue User access token value
    * @return Either facebook comment or error FacebookError
    */
  def commentResult(
    commentId        : FacebookCommentId,
    accessTokenValue : String): Future[Either[ApiError, FacebookComment]] =
    commentResult(commentId, accessToken(accessTokenValue), defaultCommentAttributeValues)

  /**
    * @param commentId Facebook comment id
    * @param accessToken User access token value
    * @return Either facebook comment or error FacebookError
    */
  def commentResult(
    commentId   : FacebookCommentId,
    accessToken : AccessToken): Future[Either[ApiError, FacebookComment]] =
    commentResult(commentId, accessToken, defaultCommentAttributeValues)

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
  def comments(
    postId      : PostId,
    accessToken : AccessToken,
    summary     : Boolean): Future[Comments] =
    sendRequestOrFail(commentsUri(postId, accessToken, defaultCommentsAttributeValues, summary))(decodeComments)

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
  def commentsResult(postId: PostId, accessTokenValue: String, summary: Boolean): FutureResult[Comments] =
    commentsResult(postId, accessToken(accessTokenValue), summary)

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessTokenValue User access token value
    * @return Either facebook comments or error FacebookError
    */
  def commentsResult(postId: PostId, accessTokenValue: String): FutureResult[Comments] =
    commentsResult(postId, accessToken(accessTokenValue), summary = false)

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken User access token
    * @param summary Boolean flag, retrieve summary or not
    * @return Either facebook comments or error FacebookError
    */
  def commentsResult(postId: PostId, accessToken: AccessToken, summary: Boolean): FutureResult[Comments] =
    sendRequest(commentsUri(postId, accessToken, defaultCommentsAttributeValues, summary))(decodeComments)

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken User access token
    * @return Either facebook comments or error FacebookError
    */
  def commentsResult(postId: PostId, accessToken: AccessToken): FutureResult[Comments] =
    commentsResult(postId, accessToken, summary = false)

}
