package domain

sealed trait TokenType

case class TokenValue(value: String) extends AnyVal

object TokenValue {
  def fromRaw(raw: String) = {
    val value = raw.split("\\|").last
    require(value.length == length)
    TokenValue(raw.split("\\|").last)
  }

  val length = 27
}

final case class AppAccessToken(tokenType: String) extends TokenType

final case class AccessToken(valueToken: TokenValue, tokenType: TokenType)