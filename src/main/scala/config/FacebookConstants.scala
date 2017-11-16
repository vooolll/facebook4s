package config

import java.time.format.DateTimeFormatter

/**
  * Facebook library constants
  */
object FacebookConstants {
  final val graphHost = "https://graph.facebook.com/"
  final val baseHost = "https://facebook.com/"
  final val oauthAccessTokenUri = "/oauth/access_token"
  final val oauthClientCodeUri = "/oauth/client_code"
  final val feedUri = "/feed"
  final val authUri = "/dialog/oauth"

  final val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
}
