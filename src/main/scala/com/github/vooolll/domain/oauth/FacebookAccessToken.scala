package com.github.vooolll.domain.oauth

import scala.concurrent.duration._

/**
  * Base trait for token type
  */
sealed trait FacebookTokenType extends Any

/**
  * Token value
  *
  * @param value string value that represents token
  */
final case class TokenValue(value: String) extends AnyVal

/**
  * Application access token - https://developers.facebook.com/docs/facebook-login/access-tokens/#apptokens
  *
  * @param oauthTokenType A token_type from oauth(https://tools.ietf.org/html/rfc6749) often has value "bearer"
  */
final case class AppAccessToken(oauthTokenType: String) extends AnyVal with FacebookTokenType

/**
  * User access token -
  * https://developers.facebook.com/docs/facebook-login/access-tokens/#usertokens
  *
  * @param oauthTokenType A token_type from oauth(https://tools.ietf.org/html/rfc6749) often has value "bearer"
  * @param expiresIn The time interval after which token will be expired
  */
final case class UserAccessToken(oauthTokenType: String, expiresIn: FiniteDuration) extends FacebookTokenType

object UserAccessToken {
  val notSpecified = UserAccessToken("bearer", 0.seconds)
}

/**
  * Base class for access token - https://developers.facebook.com/docs/facebook-login/access-tokens/
  *
  * @param tokenValue - String value that represents token
  * @param tokenType  - Type of facebook token
  */
final case class FacebookAccessToken(tokenValue: TokenValue, tokenType: FacebookTokenType)
