package com.github.vooolll.client

import com.github.vooolll.client.FacebookClient._
import com.github.vooolll.services.FacebookInternals

import scala.concurrent.Future

trait FacebookLikeApi extends FacebookInternals {

  import com.github.vooolll.serialization.FacebookDecoders.decodeLikes

  /**
    * @param postId Facebook post id
    * @param accessToken User access token
    * @param summary Boolean flag, retrieve summary or not
    * @return Facebook likes
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def likes(postId: PostId, summary: Boolean)(
    implicit accessToken: AccessToken
  ): Future[Likes] =
    sendRequestOrFail(likesUri(postId, accessToken, summary))

  /**
    * @param postId Facebook post id
    * @param accessToken User access token
    * @return Facebook likes
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def likes(postId: PostId)(implicit accessToken: AccessToken): Future[Likes] =
    likes(postId, summary = false)

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken User access token
    * @param summary Boolean flag, retrieve summary or not
    * @return Either facebook post likes or error FacebookError
    */
  def likesResult(postId: PostId, summary: Boolean)(
    implicit accessToken: AccessToken
  ): FutureResult[Likes] =
    sendRequest(likesUri(postId, accessToken, summary))

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken User access token
    * @return Either facebook post likes or error FacebookError
    */
  def likesResult(
    postId: PostId
  )(implicit accessToken: AccessToken): FutureResult[Likes] =
    likesResult(postId, summary = false)

}
