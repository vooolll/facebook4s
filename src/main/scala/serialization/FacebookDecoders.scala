package serialization

import java.time._
import java.util.Locale

import domain.feed._
import domain.oauth._
import io.circe._
import io.circe.Decoder._
import cats.syntax.either._
import config.FacebookConstants._
import domain.profile._
import org.apache.commons.lang3._

import scala.concurrent.duration._

object FacebookDecoders {
  implicit val decodeTokenType: Decoder[AppAccessToken] = decodeString.map(AppAccessToken)
  implicit val decodeTokenValue: Decoder[TokenValue] = decodeString.map(TokenValue.apply)
  implicit val decodeGender: Decoder[Gender] = decodeString.map {
    case "male"   => Gender.Male
    case "female" => Gender.Female
  }

  implicit val decodeZoneOffset: Decoder[ZoneOffset] = decodeInt.map(ZoneOffset.ofHours)

  implicit val decodeUserAccessToken: Decoder[FacebookAccessToken] = new Decoder[FacebookAccessToken] {
    override def apply(c: HCursor) = for {
      tokenValue <- c.get[TokenValue]("access_token")
      expiresIn  <- c.get[Int]("expires_in")
      tokenType  <- c.get[String]("token_type")
    } yield FacebookAccessToken(tokenValue, UserAccessToken(tokenType, expiresIn.seconds))
  }

  implicit val decodeClientCode: Decoder[FacebookClientCode] =
    Decoder.forProduct2("code", "machine_id")(FacebookClientCode.apply)

  implicit val decodeAgeRange: Decoder[AgeRange] =
    Decoder.forProduct2("min", "max")(AgeRange)

  implicit val decodeCover: Decoder[Cover] =
    Decoder.forProduct4("id", "offset_x", "offset_y", "source")(Cover)

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

  implicit val decodeLocale: Decoder[Locale] = decodeString.map(LocaleUtils.toLocale)

  implicit val decodeUser: Decoder[FacebookUser] = new Decoder[FacebookUser] {
    override def apply(c: HCursor) = for {
      id        <- c.get[FacebookUserId]("id")
      name      <- c.get[Option[String]]("name")
      firstName <- c.get[Option[String]]("first_name")
      lastName  <- c.get[Option[String]]("last_name")
      verified  <- c.get[Option[Boolean]]("verified")
      link      <- c.get[Option[String]]("link")
      picture   <- c.downField("picture").get[Option[FacebookUserPicture]]("data")
      locale    <- c.get[Option[Locale]]("locale")
      timezone  <- c.get[Option[ZoneOffset]]("timezone")
      gender    <- c.get[Option[Gender]]("gender")
      ageRange  <- c.get[Option[AgeRange]]("age_range")
      cover     <- c.get[Option[Cover]]("cover")
      updatedTime <- c.get[Option[Instant]]("updated_time")
    } yield FacebookUser(
      id, name, picture, firstName, lastName, link, verified, locale, timezone, gender, ageRange,
      cover, updatedTime)
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
