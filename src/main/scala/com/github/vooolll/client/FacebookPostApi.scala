package com.github.vooolll.client

import com.github.vooolll.client.FacebookClient._
import com.github.vooolll.domain.posts.FacebookPostAttributes._
import com.github.vooolll.services.FacebookInternals

import scala.concurrent.Future

trait FacebookPostApi extends FacebookInternals {

  import com.github.vooolll.serialization.FacebookDecoders.decodePost

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken Facebook access token
    * @param fields Sequence of facebook post attributes
    * @return Facebook post details
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def post(postId: PostId, accessToken: AccessToken, fields: Seq[PostAttribute]): Future[Post] =
    sendRequestOrFail(postUri(postId, accessToken, fields))

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken Facebook access token
    * @return Facebook post details
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def post(postId: PostId, accessToken: AccessToken): Future[Post] =
    post(postId, accessToken, defaultPostAttributeValues)

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken Facebook access token
    * @param fields Sequence of facebook post attributes
    * @return Either facebook post details or error FacebookError
    */
  def postResult(postId: PostId, accessToken: AccessToken,fields: Seq[PostAttribute]): FutureResult[Post] =
    sendRequest(postUri(postId, accessToken, fields))

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken Facebook access token
    * @return Either facebook post details or error FacebookError
    */
  def postResult(postId: PostId, accessToken: AccessToken): FutureResult[Post] =
    postResult(postId, accessToken, defaultPostAttributeValues)

}
