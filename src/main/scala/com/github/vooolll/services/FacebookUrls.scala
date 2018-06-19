package com.github.vooolll.services

import cats.implicits._
import com.github.vooolll.config.FacebookConfig.{redirectUri, version}
import com.github.vooolll.config.FacebookConstants
import com.github.vooolll.config.FacebookConstants._
import com.github.vooolll.domain.FacebookAttribute
import com.github.vooolll.domain.albums.photo.FacebookPhotoAttributes.FacebookPhotoAttribute
import com.github.vooolll.domain.albums.photo.FacebookPhotoId
import com.github.vooolll.domain.comments._
import com.github.vooolll.domain.comments.FacebookCommentsAttributes.FacebookCommentsAttribute
import com.github.vooolll.domain.comments._
import com.github.vooolll.domain.oauth._
import com.github.vooolll.domain.permission.FacebookPermissions.FacebookPermission
import com.github.vooolll.domain.posts.FacebookPostAttributes.FacebookPostAttribute
import com.github.vooolll.domain.posts.FacebookPostId
import com.github.vooolll.domain.profile.{FacebookProfileId, FacebookUserAttribute, FacebookUserId}
import org.f100ded.scalaurlbuilder.URLBuilder
import com.github.vooolll.syntax.FacebookShowOps._

import com.typesafe.scalalogging.LazyLogging

trait FacebookUrls extends LazyLogging {

  val clientId: FacebookClientId
  val appSecret: FacebookAppSecret

  lazy val graphHostBuilder = URLBuilder(base = graphHost).withPathSegments(version.show)
  lazy val baseHostBuilder = URLBuilder(base = baseHost).withPathSegments(version.show)

  lazy val oauthTokenBuilder = graphHostBuilder
    .withPathSegments(oauthAccessTokenUri)
    .withQueryParameters(
      "client_id"     -> clientId.show,
      "client_secret" -> appSecret.show)

  lazy val oauthCodeBuilder = graphHostBuilder
    .withPathSegments(oauthClientCodeUri)
    .withQueryParameters(
      "client_id"     -> clientId.show,
      "client_secret" -> appSecret.show)

  lazy val appTokenUri = oauthTokenBuilder.withQueryParameters("grant_type" -> "client_credentials")

  def userTokenUri(code: String, machineId: Option[String]) = {
    val mid = machineId.map("machine_id" -> _)
    val params = Seq(
      "redirect_uri"  -> redirect().show,
      "code"          -> code) ++ mid
    oauthTokenBuilder.withQueryParameters(params:_*)
  }

  def withAccessToken(accessToken: FacebookAccessToken) =
    graphHostBuilder.withQueryParameters("access_token" -> accessToken.show)

  def longLivedTokenUri(shortLeavingTokenValue: String) = oauthTokenBuilder.withQueryParameters(
    "grant_type"        -> "fb_exchange_token",
    "fb_exchange_token" -> shortLeavingTokenValue)

  def accessTokenCodeUri(longLeavingTokenValue: String) = oauthCodeBuilder.withQueryParameters(
    "access_token" -> longLeavingTokenValue,
    "redirect_uri" -> redirect().show)

  def friendsUri(
    accessToken: FacebookAccessToken,
    userId     : FacebookUserId,
    attributes : Seq[FacebookUserAttribute]) = edge(friendsEdge, userUri(accessToken, userId, attributes))

  def userFeedUri(
    accessToken : FacebookAccessToken,
    userId      : FacebookUserId,
    attributes  : Seq[FacebookPostAttribute]) =
    manyParams(withAccessToken(accessToken).withPathSegments(userId.show).withPathSegments(feedUri), attributes)

  def applicationUri(accessToken: FacebookAccessToken, applicationId: FacebookApplicationId) =
    withAccessToken(accessToken).withPathSegments(applicationId.show)

  def postUri(postId: FacebookPostId, accessToken: FacebookAccessToken, attributes: Seq[FacebookPostAttribute]) = {
    logger.debug("sending request : " + manyParams(graphHostBuilder.withPathSegments(postId.show), attributes))
    manyParams(withAccessToken(accessToken).withPathSegments(postId.show), attributes)
  }

  def photoUri(photoId: FacebookPhotoId, accessToken: FacebookAccessToken, attributes: Seq[FacebookPhotoAttribute]) =
    manyParams(withAccessToken(accessToken).withPathSegments(photoId.show), attributes)

  def likesUri(postId: FacebookPostId, accessToken: FacebookAccessToken, summary: Boolean = false) =
    withSummary(edge(likeUri, postUri(postId, accessToken, Nil)), summary)

  def commentsUri(
    postId      : FacebookPostId,
    accessToken : FacebookAccessToken,
    attributes  : Seq[FacebookCommentsAttribute],
    summary     : Boolean = false) =
    manyParams(withSummary(edge(commentsEdge, postUri(postId, accessToken, Nil)), summary), attributes)

  def commentUri(
    commentId   : FacebookCommentId,
    accessToken : FacebookAccessToken,
    attributes  : Seq[FacebookCommentAttribute]) =
    manyParams(withAccessToken(accessToken).withPathSegments(commentId.value), attributes)

  def albumsUri(profileId: FacebookProfileId, accessToken: FacebookAccessToken) =
    edge(albumsEdge, profileUri(accessToken, profileId))

  def buildAuthUrl(permissions: Seq[FacebookPermission],
                   responseType: FacebookAttribute = FacebookCode,
                   state: Option[String] = None) = {
    val params = Seq(
      "client_id"     -> clientId.show,
      "redirect_uri"  -> redirect().show,
      "response_type" -> responseType.show
    ) ++ many("scope", permissions) ++ state.map("state" -> _)
    baseHostBuilder.withPathSegments(FacebookConstants.authUri).withQueryParameters(params:_*)
  }

  def profileUri(accessToken : FacebookAccessToken,
                 profileId   : FacebookProfileId) =
    withAccessToken(accessToken).withPathSegments(profileId.value)

  def userUri(accessToken : FacebookAccessToken,
              userId      : FacebookUserId = FacebookUserId("me"),
              attributes  : Seq[FacebookUserAttribute]) = {
    val url = manyParams(withAccessToken(accessToken).withPathSegments(userId.show), attributes)
    url
  }

  private[this] def manyParams(url: URLBuilder, attr: Seq[FacebookAttribute]) =
    url.withQueryParameters(Seq() ++ many("fields", attr):_*)

  private[this] def edge(edge: String, uriBuilder: URLBuilder) =
    uriBuilder.withPathSegments(edge)

  private[this] def withSummary(uriBuilder: URLBuilder, summary: Boolean) =
    uriBuilder.withQueryParameters("summary" -> summary.toString)

  private[this] def redirect() = {
    redirectUri getOrElse(throw new RuntimeException("redirect uri is not set"))
  }

  private[this] def many(key: String, attr: Seq[FacebookAttribute]) =
    if (attr.nonEmpty) key -> commaSeparated(attr) some else none

  private[this] def commaSeparated(permissions: Seq[FacebookAttribute]) = permissions.map(_.show).mkString(",")

}
