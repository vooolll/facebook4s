package domain

import TokenValue._

sealed trait FacebookTokenType

final case class TokenValue(value: String) extends FixedSizeValue(length, message)

abstract class FixedSizeValue(length: Int, msg: String = "") {
  require(value.length == length, msg)
  def value: String
}

object TokenValue {
  val length = 27
  val message = "Wrong facebook access token length"

  def fromRaw(raw: String) = TokenValue(raw.split("\\|").last)
}

final case class AppAccessToken(tokenType: String) extends FacebookTokenType

final case class FacebookAccessToken(valueToken: TokenValue, tokenType: FacebookTokenType)