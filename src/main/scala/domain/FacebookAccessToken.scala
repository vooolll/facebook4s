package domain

import java.time.Instant

import scala.concurrent.duration.FiniteDuration

sealed trait FacebookTokenType

final case class TokenValue(value: String)

abstract class FixedSizeValue(length: Int, msg: String = "") {
  require(value.length == length, msg)
  def value: String
}

object TokenValue {

  def fromRaw(raw: String) = TokenValue(raw.split("\\|").last)
}

final case class AppAccessToken(oauthTokenType: String) extends FacebookTokenType

final case class UserAccessToken(oauthTokenType: String, expiresIn: FiniteDuration) extends FacebookTokenType

final case class FacebookAccessToken(tokenValue: TokenValue, tokenType: FacebookTokenType)