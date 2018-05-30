package com.github.vooolll.client

import com.github.vooolll.client.FacebookClient._
import com.github.vooolll.domain.posts.FacebookPostId
import com.github.vooolll.services.FacebookInternals

import scala.concurrent.Future

trait FacebookLikeApi extends FacebookInternals {

  import com.github.vooolll.serialization.FacebookDecoders.decodeLikes

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
    * @return Either facebook post likes or error FacebookError
    */
  def likesResult(postId: PostId, accessTokenValue: String, summary: Boolean): FutureResult[Likes] =
    likesResult(postId, accessToken(accessTokenValue), summary)

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessTokenValue User access token value
    * @return Either facebook post likes or error FacebookError
    */
  def likesResult(postId: PostId, accessTokenValue: String): FutureResult[Likes] =
    likesResult(postId, accessToken(accessTokenValue), summary = false)

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken User access token
    * @param summary Boolean flag, retrieve summary or not
    * @return Either facebook post likes or error FacebookError
    */
  def likesResult(postId: PostId, accessToken: AccessToken, summary: Boolean): FutureResult[Likes] =
    sendRequest(likesUri(postId, accessToken, summary))

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken User access token
    * @return Either facebook post likes or error FacebookError
    */
  def likesResult(postId: PostId, accessToken: AccessToken): FutureResult[Likes] =
    likesResult(postId, accessToken, summary = false)

}
