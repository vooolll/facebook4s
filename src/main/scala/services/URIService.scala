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
      "grant_type"    -> "client_credentials"
    )

  def userTokenURI(code: String) = URLBuilder(base = host)
    .withPathSegments(version.show, oauthUri)
    .withQueryParameters(
      "client_id"     -> clientId.show,
      "client_secret" -> appSecret.show,
      "redirect_uri"  -> redirectUri.show,
      "code"          -> code
    )
}

object URIService {
  def apply(): URIService = new URIService(clientId, appSecret)
}
