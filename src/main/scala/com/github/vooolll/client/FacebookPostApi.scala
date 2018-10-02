package com.github.vooolll.client

import com.github.vooolll.client.FacebookClient._
import com.github.vooolll.domain.posts.FacebookPostAttributes._
import com.github.vooolll.services.DomainParsing.PostRequestContext
import com.github.vooolll.services.FacebookInternals

import scala.concurrent.Future

trait FacebookPostApi extends FacebookInternals {

  import com.github.vooolll.serialization.FacebookDecoders.decodePost
  import com.github.vooolll.serialization.FacebookDecoders.decodePostId

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken Facebook access token
    * @param fields Set of facebook post attributes
    * @return Facebook post details
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def post(postId: PostId, fields: Set[_ <: PostAttribute])(implicit accessToken: AccessToken): Future[Post] =
    sendRequestOrFail(postUri(postId, accessToken, fields))(decodePost)

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
    * @param fields Set of facebook post attributes
    * @return Either facebook post details or error FacebookError
    */
  def postResult(postId: PostId, fields: Set[_ <: PostAttribute])(implicit accessToken: AccessToken): FutureResult[Post] =
    sendRequest(postUri(postId, accessToken, fields))(decodePost)

  /**
    * @param postId Id of facebook post alpha numeric
    * @param accessToken Facebook access token
    * @return Either facebook post details or error FacebookError
    */
  def postResult(postId: PostId)(implicit accessToken: AccessToken): FutureResult[Post] =
    postResult(postId, defaultPostAttributeValues)

  /**
    * @param createPost New post information
    * @param pageId Id of page where post will be created
    * @param accessToken Facebook access token
    * @return Either Page id of newly created post or FacebookError
    */
  def createPostResult(createPost: CreatePost, pageId: PageId)(implicit accessToken: AccessToken): FutureResult[PostId] =
    sendRequest(pageUri(pageId, accessToken), PostRequestContext(createPost.toMap))(decodePostId)

  /**
    * @param createPost New post information
    * @param pageId  Id of page where post will be created
    * @param accessToken Facebook access token
    * @return Page id of newly created post
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def createPost(createPost: CreatePost, pageId: PageId)(implicit accessToken: AccessToken): Future[PostId] =
    sendRequestOrFail(pageUri(pageId, accessToken), PostRequestContext(createPost.toMap))(decodePostId)

}
