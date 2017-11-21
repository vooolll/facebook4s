package serialization

import java.time._

import domain.feed._
import domain.oauth._
import io.circe._
import io.circe.Decoder._
import cats.syntax.either._
import config.FacebookConstants._
import domain.profile.FacebookApplication

import scala.concurrent.duration._

object FacebookDecoders {
  implicit val decodeTokenType: Decoder[AppAccessToken] = decodeString.map(AppAccessToken)
  implicit val decodeTokenValue: Decoder[TokenValue] = decodeString.map(TokenValue.apply)

  implicit val decodeUserAccessToken: Decoder[FacebookAccessToken] = new Decoder[FacebookAccessToken] {
    override def apply(c: HCursor) = for {
      tokenValue <- c.downField("access_token").as[TokenValue]
      expiresIn  <- c.downField("expires_in").as[Int]
      tokenType  <- c.downField("token_type").as[String]
    } yield FacebookAccessToken(tokenValue, UserAccessToken(tokenType, expiresIn.seconds))
  }

  implicit val decodeClientCode: Decoder[FacebookClientCode] =
    Decoder.forProduct2("code", "machine_id")(FacebookClientCode.apply)

  implicit val decodeAppAccessToken: Decoder[FacebookAccessToken] = new Decoder[FacebookAccessToken] {
    override def apply(c: HCursor) = for {
      tokenValue <- c.downField("access_token").as[TokenValue]
      tokenType  <- c.downField("token_type").as[String]
    } yield FacebookAccessToken(tokenValue, AppAccessToken(tokenType))
  }

  implicit val decodeError: Decoder[FacebookError] = Decoder.forProduct1("message")(FacebookError)

  implicit val decodeOauthError: Decoder[FacebookOauthError] = new Decoder[FacebookOauthError]{
    def apply(c: HCursor): Result[FacebookOauthError] = for {
      error <- c.downField("error").as[FacebookError]
    } yield FacebookOauthError(error)
  }

  implicit val decodeInstant: Decoder[Instant] = decodeString.emap { str =>
    Either.catchNonFatal(dateFormat.parse(str, Instant.from(_))).leftMap(t => "Instant")
  }

  implicit val decodeApplication: Decoder[FacebookApplication] =
    Decoder.forProduct3("id", "link", "name")(FacebookApplication)

  implicit val decodePost: Decoder[FacebookSimplePost] =
    Decoder.forProduct3("id", "story", "created_time")(FacebookSimplePost)

  implicit val decodePaging: Decoder[FacebookPaging] =
    Decoder.forProduct2("next", "previous")(FacebookPaging)


  implicit val decodeFeed: Decoder[FacebookFeed] = new Decoder[FacebookFeed] {
    override def apply(c: HCursor) = for {
      posts  <- c.downField("data").as[List[FacebookSimplePost]]
      paging <- c.downField("paging").as[FacebookPaging]
    } yield FacebookFeed(posts, paging)
  }

}
