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
  def post(postId: PostId, fields: Seq[PostAttribute])(implicit accessToken: AccessToken): Future[Post] =
    sendRequestOrFail(postUri(postId, accessToken, fields))

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken Facebook access token
    * @return Facebook post details
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def post(postId: PostId)(implicit accessToken: AccessToken): Future[Post] =
    post(postId, defaultPostAttributeValues)

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken Facebook access token
    * @param fields Sequence of facebook post attributes
    * @return Either facebook post details or error FacebookError
    */
  def postResult(postId: PostId, fields: Seq[PostAttribute])(implicit accessToken: AccessToken): FutureResult[Post] =
    sendRequest(postUri(postId, accessToken, fields))

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken Facebook access token
    * @return Either facebook post details or error FacebookError
    */
  def postResult(postId: PostId)(implicit accessToken: AccessToken): FutureResult[Post] =
    postResult(postId, defaultPostAttributeValues)

}
