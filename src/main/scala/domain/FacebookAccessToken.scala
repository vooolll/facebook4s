package domain

import domain.TokenValue._

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


final case class FacebookAccessToken(valueToken: TokenValue, tokenType: FacebookTokenType)