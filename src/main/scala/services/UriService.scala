package services

import cats.implicits._
import config.FacebookConfig.{appSecret, clientId, redirectUri, version}
import config.FacebookConstants
import config.FacebookConstants._
import domain.FacebookUserId
import other.FacebookShowOps._
import domain.oauth.{FacebookAccessToken, FacebookAppSecret, FacebookClientId}
import domain.permission.FacebookPermissions.FacebookUserPermission
import org.f100ded.scalaurlbuilder.URLBuilder

/**
  * Service that constructs uri to facebook api
  * @param clientId client id(application id)
  * @param appSecret application secret
  */
class UriService(clientId: FacebookClientId, appSecret: FacebookAppSecret) {

  val hostBuilder = URLBuilder(base = host).withPathSegments(version.show)

  val oauthTokenBuilder = hostBuilder
    .withPathSegments(oauthAccessTokenUri)
    .withQueryParameters(
      "client_id"     -> clientId.show,
      "client_secret" -> appSecret.show)

  val oauthCodeBuilder = hostBuilder
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

  def longLivedTokenUri(shortLeavingTokenValue: String) = oauthTokenBuilder.withQueryParameters(
    "grant_type"        -> "fb_exchange_token",
    "fb_exchange_token" -> shortLeavingTokenValue)

  def accessTokenCodeUri(longLeavingTokenValue: String) = oauthCodeBuilder.withQueryParameters(
    "access_token" -> longLeavingTokenValue,
    "redirect_uri" -> redirectUri.show)

  def userFeedUri(accessToken: FacebookAccessToken, userId: FacebookUserId = FacebookUserId("me")) =
    hostBuilder.withPathSegments(userId.show).withPathSegments(feedUri)
      .withQueryParameters("access_token" -> accessToken.show)

  def authUrl(permissions: Seq[FacebookUserPermission]) = {
    val params = Seq(
      "client_id"     -> clientId.show,
      "redirect_uri"  -> redirectUri.show
    ) ++ scope(permissions)
    hostBuilder.withPathSegments(FacebookConstants.authUri).withQueryParameters(params:_*)
  }

  private def scope(permissions: Seq[FacebookUserPermission]) =
    if (permissions.nonEmpty) Some("scope" -> commaSeparated(permissions)) else None

  private def commaSeparated(permissions: Seq[FacebookUserPermission]) =
    permissions.map(_.value).mkString(",")
}

object UriService {
  def apply() = new UriService(clientId, appSecret)

  def apply(clientId: FacebookClientId, appSecret: FacebookAppSecret) = new UriService(clientId, appSecret)
}
