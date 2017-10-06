package services

import cats.implicits._
import config.FacebookConfig.{appSecret, clientId, redirectUri, version}
import config.FacebookConstants.{host, oauthClientCodeUri, oauthAccessTokenUri}
import domain.FacebookShowOps._
import domain.{FacebookAppSecret, FacebookClientId}
import org.f100ded.scalaurlbuilder.URLBuilder

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

  def userTokenUri(code: String) = oauthTokenBuilder.withQueryParameters(
    "redirect_uri"  -> redirectUri.show,
    "code"          -> code)

  def longLivedTokenUri(shortLeavingTokenValue: String) = oauthTokenBuilder.withQueryParameters(
    "grant_type"        -> "fb_exchange_token",
    "fb_exchange_token" -> shortLeavingTokenValue)

  def accessTokenCodeUri(longLeavingTokenValue: String) = oauthCodeBuilder.withQueryParameters(
    "access_token" -> longLeavingTokenValue,
    "redirect_uri" -> redirectUri.show)
}

object UriService {
  def apply() = new UriService(clientId, appSecret)

  def apply(clientId: FacebookClientId, appSecret: FacebookAppSecret) = new UriService(clientId, appSecret)
}
