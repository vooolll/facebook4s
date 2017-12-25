package client

import client.FacebookClient._
import domain.posts.FacebookPostAttributes._
import domain.posts._
import services.FacebookInternals

import scala.concurrent.Future

trait FacebookPostApi extends FacebookInternals {

  type PostId = FacebookPostId
  type Post = FacebookPost
  type AsyncPostResult = Future[Either[ApiError, Post]]


  import serialization.FacebookDecoders._
  import uriService._

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken Facebook access token
    * @param fields Sequence of facebook post attributes
    * @return Facebook post details
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def post(postId: PostId, accessToken: AccessToken, fields: Seq[FacebookPostAttribute]): Future[Post] =
    sendRequestOrFail(postUri(postId, accessToken, fields))(decodePost)

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
    * @return Either facebook post details or error FacebookOauthError
    */
  def postResult(postId: PostId, accessToken: AccessToken,fields: Seq[FacebookPostAttribute]): AsyncPostResult =
    sendRequest(postUri(postId, accessToken, fields))(decodePost)

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken Facebook access token
    * @return Either facebook post details or error FacebookOauthError
    */
  def postResult(postId: PostId, accessToken: AccessToken): AsyncPostResult =
    postResult(postId, accessToken, defaultPostAttributeValues)

}
