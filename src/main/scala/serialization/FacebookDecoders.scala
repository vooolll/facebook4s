package serialization

import java.time._

import domain.feed._
import domain.oauth._
import io.circe._
import io.circe.Decoder._
import cats.syntax.either._
import config.FacebookConstants._
import domain.profile.{FacebookApplication, FacebookUser, FacebookUserId, FacebookUserPicture}

import scala.concurrent.duration._

object FacebookDecoders {
  implicit val decodeTokenType: Decoder[AppAccessToken] = decodeString.map(AppAccessToken)
  implicit val decodeTokenValue: Decoder[TokenValue] = decodeString.map(TokenValue.apply)

  implicit val decodeUserAccessToken: Decoder[FacebookAccessToken] = new Decoder[FacebookAccessToken] {
    override def apply(c: HCursor) = for {
      tokenValue <- c.get[TokenValue]("access_token")
      expiresIn  <- c.get[Int]("expires_in")
      tokenType  <- c.get[String]("token_type")
    } yield FacebookAccessToken(tokenValue, UserAccessToken(tokenType, expiresIn.seconds))
  }

  implicit val decodeClientCode: Decoder[FacebookClientCode] =
    Decoder.forProduct2("code", "machine_id")(FacebookClientCode.apply)

  implicit val decodeAppAccessToken: Decoder[FacebookAccessToken] = new Decoder[FacebookAccessToken] {
    override def apply(c: HCursor) = for {
      tokenValue <- c.get[TokenValue]("access_token")
      tokenType  <- c.get[String]("token_type")
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

  implicit val decodeUserId: Decoder[FacebookUserId] = decodeString.map(FacebookUserId)
  implicit val decodeAppId: Decoder[FacebookAppId] = decodeString.map(FacebookAppId)
  implicit val decodeClientId: Decoder[FacebookClientId] = decodeString.map(FacebookClientId.apply)

  implicit val decodeUserPicture: Decoder[FacebookUserPicture] =
    Decoder.forProduct4("height", "is_silhouette", "url", "width")(FacebookUserPicture)

  implicit val decodeUser: Decoder[FacebookUser] = new Decoder[FacebookUser] {
    override def apply(c: HCursor) = for {
      id      <- c.get[FacebookUserId]("id")
      name    <- c.get[String]("name")
      picture <- c.downField("picture").get[Option[FacebookUserPicture]]("data")
    } yield FacebookUser(id, name, picture)
  }

  implicit val decodeApplication: Decoder[FacebookApplication] = new Decoder[FacebookApplication] {
    override def apply(c: HCursor) = for {
      id     <- c.get[FacebookAppId]("id")
      link   <- c.get[String]("link")
      name   <- c.get[String]("name")
    } yield FacebookApplication(id, link, name)
  }

  implicit val decodePost: Decoder[FacebookSimplePost] =
    Decoder.forProduct3("id", "story", "created_time")(FacebookSimplePost)

  implicit val decodePaging: Decoder[FacebookPaging] =
    Decoder.forProduct2("next", "previous")(FacebookPaging)


  implicit val decodeFeed: Decoder[FacebookFeed] = new Decoder[FacebookFeed] {
    override def apply(c: HCursor) = for {
      posts  <- c.get[List[FacebookSimplePost]]("data")
      paging <- c.get[FacebookPaging]("paging")
    } yield FacebookFeed(posts, paging)
  }

}
