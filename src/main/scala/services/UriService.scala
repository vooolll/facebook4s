package services

import cats.implicits._
import config.FacebookConfig.{appSecret, clientId, redirectUri, version}
import config.FacebookConstants
import config.FacebookConstants._
import domain.oauth._
import domain.permission.FacebookPermissions.FacebookPermission
import domain.posts.FacebookPostAttributes.FacebookPostAttribute
import domain.posts.FacebookPostId
import domain.profile.{FacebookUserAttribute, FacebookUserId}
import org.f100ded.scalaurlbuilder.URLBuilder
import syntax.FacebookShowOps._
/**
  * Service that constructs uri to facebook api
  * @param clientId client id(application id)
  * @param appSecret application secret
  */
class UriService(clientId: FacebookClientId, appSecret: FacebookAppSecret) {

  val graphHostBuilder = URLBuilder(base = graphHost).withPathSegments(version.show)
  val baseHostBuilder = URLBuilder(base = baseHost).withPathSegments(version.show)

  val oauthTokenBuilder = graphHostBuilder
    .withPathSegments(oauthAccessTokenUri)
    .withQueryParameters(
      "client_id"     -> clientId.show,
      "client_secret" -> appSecret.show)

  val oauthCodeBuilder = graphHostBuilder
    .withPathSegments(oauthClientCodeUri)
    .withQueryParameters(
      "client_id"     -> clientId.show,
      "client_secret" -> appSecret.show)

  val appTokenUri = oauthTokenBuilder.withQueryParameters("grant_type" -> "client_credentials")

  def userTokenUri(code: String, machineId: Option[String]) = {
    val mid = machineId.map("machine_id" -> _)
    val params = Seq(
      "redirect_uri"  -> redirectUri.show,
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
    "redirect_uri" -> redirectUri.show)

  def userFeedUri(accessToken : FacebookAccessToken,
                  userId      : FacebookUserId = FacebookUserId("me"),
                  attributes  : Seq[FacebookPostAttribute]) =
    manyParams(withAccessToken(accessToken).withPathSegments(userId.show).withPathSegments(feedUri), attributes)

  def applicationUri(accessToken: FacebookAccessToken, applicationId: FacebookApplicationId) =
    withAccessToken(accessToken).withPathSegments(applicationId.show)

  def postUri(postId: FacebookPostId, accessToken: FacebookAccessToken, attributes: Seq[FacebookPostAttribute]) =
    withAccessToken(accessToken).withPathSegments(postId.show)

  def authUrl(permissions: Seq[FacebookPermission],
              responseType: FacebookOauthResponseType = FacebookCode,
              state: Option[String] = None) = {
    val params = Seq(
      "client_id"     -> clientId.show,
      "redirect_uri"  -> redirectUri.show,
      "response_type" -> responseType.show
    ) ++ many("scope", permissions) ++ state.map("state" -> _)
    baseHostBuilder.withPathSegments(FacebookConstants.authUri).withQueryParameters(params:_*)
  }

  def userUri(accessToken : FacebookAccessToken,
              userId      : FacebookUserId = FacebookUserId("me"),
              attributes  : Seq[FacebookUserAttribute]) =
    manyParams(withAccessToken(accessToken).withPathSegments(userId.show), attributes)

  private def manyParams(url: URLBuilder, attr: Seq[HasStringValue]) =
    url.withQueryParameters(Seq() ++ many("fields", attr):_*)

  private def many(key: String, attr: Seq[HasStringValue]) =
    if (attr.nonEmpty) key -> commaSeparated(attr) some else none

  private def commaSeparated(permissions: Seq[HasStringValue]) = permissions.map(_.show).mkString(",")

}

object UriService {
  def apply() = new UriService(clientId, appSecret)

  def apply(clientId: FacebookClientId, appSecret: FacebookAppSecret) = new UriService(clientId, appSecret)
}
