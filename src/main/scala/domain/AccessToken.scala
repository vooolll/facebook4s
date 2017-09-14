package domain

sealed trait TokenType

final case class TokenValue(value: String) extends AnyVal

object TokenValue {
  def fromRaw(raw: String) = TokenValue(raw.split("\\|").last)
}

final case class AppAccessToken(tokenType: String) extends TokenType

final case class AccessToken(valueToken: TokenValue, tokenType: TokenType)