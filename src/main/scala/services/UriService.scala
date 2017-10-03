package services

import cats.implicits._
import config.FacebookConfig.{appSecret, clientId, redirectUri, version}
import config.FacebookConstants.{host, oauthUri}
import domain.FacebookShowOps._
import domain.{FacebookAppSecret, FacebookClientId}
import org.f100ded.scalaurlbuilder.URLBuilder

class UriService(clientId: FacebookClientId, appSecret: FacebookAppSecret) {

  val hostBuilder = URLBuilder(base = host)

  val appTokenUri = hostBuilder
    .withPathSegments(version.show, oauthUri)
    .withQueryParameters(
      "client_id"     -> clientId.show,
      "client_secret" -> appSecret.show,
      "grant_type"    -> "client_credentials")

  def userTokenUri(code: String) = hostBuilder
    .withPathSegments(version.show, oauthUri)
    .withQueryParameters(
      "client_id"     -> clientId.show,
      "client_secret" -> appSecret.show,
      "redirect_uri"  -> redirectUri.show,
      "code"          -> code)

  def longLivedTokenUri(shortLeavingTokenValue: String) = hostBuilder
    .withPathSegments(version.show, oauthUri)
    .withQueryParameters(
      "client_id"         -> clientId.show,
      "client_secret"     -> appSecret.show,
      "grant_type"        -> "fb_exchange_token",
      "fb_exchange_token" -> shortLeavingTokenValue)

  def tokenUri(code: Option[String] = None) = code match {
    case Some(code) => userTokenUri(code)
    case _ => appTokenUri
  }
}

object UriService {
  def apply() = new UriService(clientId, appSecret)

  def apply(clientId: FacebookClientId, appSecret: FacebookAppSecret) = new UriService(clientId, appSecret)
}
