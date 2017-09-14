package domain

sealed trait FacebookTokenType

final case class TokenValue(value: String) extends FixedSizeValue(TokenValue.length)

abstract class FixedSizeValue(length: Int) {
  require(value.length == length)
  def value: String
}

object TokenValue {
  val length = 27

  def fromRaw(raw: String) = TokenValue(raw.split("\\|").last)
}

final case class AppAccessToken(tokenType: String) extends FacebookTokenType

final case class FacebookAccessToken(valueToken: TokenValue, tokenType: FacebookTokenType)