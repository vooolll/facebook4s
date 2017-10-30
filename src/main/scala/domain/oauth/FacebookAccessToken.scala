package domain.oauth

import domain.permission.FacebookPermissions.FacebookUserPermission

import scala.concurrent.duration.FiniteDuration

/**
  * Base trait for token type
  */
sealed trait FacebookTokenType

/**
  * Token value
  * @param value string value that represents token
  */
final case class TokenValue(value: String)

object TokenValue {
  def fromRaw(raw: String) = TokenValue(raw.split("\\|").last)
}

abstract class FixedSizeValue(length: Int, msg: String = "") {
  require(value.length == length, msg)
  def value: String
}

/**
  * Application access token - https://developers.facebook.com/docs/facebook-login/access-tokens/#apptokens
  * @param oauthTokenType A token_type from oauth(https://tools.ietf.org/html/rfc6749) often has value "bearer"
  */
final case class AppAccessToken(oauthTokenType: String) extends FacebookTokenType

/**
  * User access token -
  * https://developers.facebook.com/docs/facebook-login/access-tokens/#usertokens
  * @param oauthTokenType A token_type from oauth(https://tools.ietf.org/html/rfc6749) often has value "bearer"
  * @param expiresIn The time interval after which token will be expired
  */
final case class UserAccessToken(
  oauthTokenType : String,
  expiresIn      : FiniteDuration,
  permissions    : Seq[FacebookUserPermission] = Nil) extends FacebookTokenType

/**
  * Base class for access token - https://developers.facebook.com/docs/facebook-login/access-tokens/
  * @param tokenValue - String value that represents token
  * @param tokenType  - Type of facebook token
  */
final case class FacebookAccessToken(tokenValue: TokenValue, tokenType: FacebookTokenType)