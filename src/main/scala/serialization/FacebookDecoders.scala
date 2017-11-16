package serialization

import domain.oauth._
import io.circe.{Decoder, HCursor}
import io.circe.Decoder._

import scala.concurrent.duration._

object FacebookDecoders {
  implicit val decodeTokenType: Decoder[AppAccessToken] = decodeString.map(AppAccessToken)
  implicit val decodeTokenValue: Decoder[TokenValue] = decodeString.map(TokenValue.apply)

  implicit val decodeUserAccessToken: Decoder[FacebookAccessToken] = new Decoder[FacebookAccessToken] {
    override def apply(c: HCursor) = {
      for {
        tokenValue <- c.downField("access_token").as[TokenValue]
        expiresIn <- c.downField("expires_in").as[Int]
        tokenType <- c.downField("token_type").as[String]
      } yield FacebookAccessToken(tokenValue, UserAccessToken(tokenType, expiresIn.seconds))
    }
  }

  implicit val decodeClientCode: Decoder[FacebookClientCode] =
    Decoder.forProduct2("code", "machine_id")(FacebookClientCode.apply)

  implicit val decodeAppAccessToken: Decoder[FacebookAccessToken] = new Decoder[FacebookAccessToken] {
    override def apply(c: HCursor) = {
      for {
        tokenValue <- c.downField("access_token").as[TokenValue]
        tokenType <- c.downField("token_type").as[String]
      } yield FacebookAccessToken(tokenValue, AppAccessToken(tokenType))
    }
  }
}
