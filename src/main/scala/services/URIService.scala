package services

import cats.implicits._
import config.FacebookConfig.{appSecret, clientId, redirectUri, version}
import config.FacebookConstants.{host, oauthUri}
import domain.FacebookShowOps._
import domain.{FacebookAppSecret, FacebookClientId}
import org.f100ded.scalaurlbuilder.URLBuilder

class URIService(clientId: FacebookClientId, appSecret: FacebookAppSecret) {

  val appTokenURI = URLBuilder(base = host)
    .withPathSegments(version.show, oauthUri)
    .withQueryParameters(
      "client_id"     -> clientId.show,
      "client_secret" -> appSecret.show,
      "grant_type"    -> "client_credentials")

  def userTokenURI(code: String) = URLBuilder(base = host)
    .withPathSegments(version.show, oauthUri)
    .withQueryParameters(
      "client_id"     -> clientId.show,
      "client_secret" -> appSecret.show,
      "redirect_uri"  -> redirectUri.show,
      "code"          -> code)

  def tokenUri(code: Option[String] = None) = code match {
    case Some(code) => userTokenURI(code)
    case _ => appTokenURI
  }
}

object URIService {
  def apply() = new URIService(clientId, appSecret)

  def apply(clientId: FacebookClientId, appSecret: FacebookAppSecret) = new URIService(clientId, appSecret)
}
