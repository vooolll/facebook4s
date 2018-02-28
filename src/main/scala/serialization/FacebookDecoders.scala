package serialization

import java.net.URL
import java.time._
import java.util.Locale

import domain.feed._
import domain.oauth._
import io.circe._
import io.circe.Decoder._
import cats.syntax.either._
import config.FacebookConstants._
import domain._
import domain.comments._
import domain.likes._
import domain.oauth.FacebookError.FacebookErrorType
import domain.posts._
import domain.profile._
import org.apache.commons.lang3._
import FacebookErrorCodeDecoders._
import domain.albums.{FacebookAlbum, FacebookAlbumId}
import domain.albums.image.FacebookImage
import domain.albums.photo.{FacebookPhoto, FacebookPhotoId}

import scala.concurrent.duration._

object FacebookDecoders {
  implicit val decodeTokenType: Decoder[AppAccessToken] = decodeString.map(AppAccessToken)
  implicit val decodeTokenValue: Decoder[TokenValue] = decodeString.map(TokenValue.apply)
  implicit val decodePostId: Decoder[FacebookPostId] = decodeString.map(FacebookPostId.apply)
  implicit val decodeGender: Decoder[Gender] = decodeString.map {
    case "male"   => Gender.Male
    case "female" => Gender.Female
  }

  implicit val decodeUrl: Decoder[URL] = decodeString.map(s => new URL(s))

  implicit val decodeProfileId: Decoder[FacebookProfileId] = decodeString.map(FacebookProfileId)

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

  implicit val decodeError: Decoder[FacebookError] = new Decoder[FacebookError] {
    override def apply(c: HCursor) = for {
      message   <- c.downField("error").get[String]("message")
      errorType <- c.downField("error").get[FacebookErrorType]("code")
    } yield FacebookError(message, errorType)
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

  implicit val decodePost: Decoder[FacebookPost] = new Decoder[FacebookPost] {
    override def apply(c: HCursor) = for {
      id          <- c.get[FacebookPostId]("id")
      name        <- c.get[Option[String]]("story")
      createdTime <- c.get[Option[Instant]]("created_time")
      objectId    <- c.get[Option[String]]("object_id")
      picture     <- c.get[Option[URL]]("picture")
      from        <- c.downField("from").get[Option[FacebookProfileId]]("id")
    } yield FacebookPost(id, name, createdTime, objectId, picture, from)
  }

  implicit val decodeFeedPaging: Decoder[FacebookFeedPaging] =
    Decoder.forProduct2("next", "previous")(FacebookFeedPaging)


  implicit val decodeFeed: Decoder[FacebookFeed] = new Decoder[FacebookFeed] {
    override def apply(c: HCursor) = for {
      posts  <- c.get[List[FacebookPost]]("data")
      paging <- c.get[FacebookFeedPaging]("paging")
    } yield FacebookFeed(posts, paging)
  }

  implicit val decodeLikesSummary: Decoder[FacebookLikesSummary] = new Decoder[FacebookLikesSummary] {
    override def apply(c: HCursor) = for {
      totalCount <- c.get[Int]("total_count")
      canLike    <- c.get[Option[Boolean]]("can_like")
      hasLikes   <- c.get[Option[Boolean]]("has_liked")
    } yield FacebookLikesSummary(totalCount, canLike, hasLikes)
  }

  implicit val decodeLike: Decoder[FacebookLike] = new Decoder[FacebookLike] {
    override def apply(c: HCursor) = for {
      id   <- c.get[FacebookUserId]("id")
      name <- c.get[Option[String]]("name")
    } yield FacebookLike(id, name)
  }

  implicit val decodePaging: Decoder[FacebookPaging] = new Decoder[FacebookPaging] {
    override def apply(c: HCursor) = for {
      before <- c.downField("cursors").get[Option[String]]("before")
      after  <- c.downField("cursors").get[Option[String]]("after")
    } yield FacebookPaging(before, after)
  }


  implicit val decodeLikes: Decoder[FacebookLikes] = new Decoder[FacebookLikes] {
    override def apply(c: HCursor) = for {
      likes   <- c.get[List[FacebookLike]]("data")
      paging  <- c.get[FacebookPaging]("paging")
      summary <- c.get[Option[FacebookLikesSummary]]("summary")
    } yield FacebookLikes(likes, paging, summary)
  }

  implicit val decodeCommentId: Decoder[FacebookCommentId] = decodeString.map(FacebookCommentId)

  implicit val decodeComment: Decoder[FacebookComment] = new Decoder[FacebookComment] {
    override def apply(c: HCursor) = for {
      id          <- c.get[FacebookCommentId]("id")
      message     <- c.get[Option[String]]("message")
      from        <- c.downField("from").get[Option[FacebookProfileId]]("id")
      createdTime <- c.get[Option[Instant]]("created_time")
    } yield FacebookComment(id, message, createdTime, from)
  }

  implicit val decodeOrder: Decoder[FacebookOrder] = decodeString map {
    case "ranked"                => FacebookOrder.Ranked
    case "chronological"         => FacebookOrder.Chronological
    case "reverse_chronological" => FacebookOrder.ReverseChronological
  }

  implicit val decodeCommentSummary: Decoder[FacebookCommentSummary] = new Decoder[FacebookCommentSummary] {
    override def apply(c: HCursor) = for {
      order      <- c.get[FacebookOrder]("order")
      totalCount <- c.get[Int]("total_count")
      canComment <- c.get[Option[Boolean]]("can_comment")
    } yield FacebookCommentSummary(order, totalCount, canComment)
  }

  implicit val decodeComments: Decoder[FacebookComments] = new Decoder[FacebookComments] {
    override def apply(c: HCursor) = for {
      comments <- c.get[List[FacebookComment]]("data")
      paging   <- c.get[Option[FacebookPaging]]("paging")
      summary  <- c.get[Option[FacebookCommentSummary]]("summary")
    } yield FacebookComments(comments, paging, summary)
  }

  implicit val decodeImage: Decoder[FacebookImage] = new Decoder[FacebookImage] {
    override def apply(c: HCursor) = for {
      height <- c.get[Double]("height")
      source <- c.get[URL]("source")
      width  <- c.get[Double]("width")
    } yield FacebookImage(height, source, width)
  }

  implicit val decodeAlbumId: Decoder[FacebookAlbumId] = decodeString.map(FacebookAlbumId)

  implicit val decodeAlbum: Decoder[FacebookAlbum] = new Decoder[FacebookAlbum] {
    override def apply(c: HCursor) = for {
      id          <- c.get[FacebookAlbumId]("id")
      name        <- c.get[String]("name")
      createdTime <- c.get[Instant]("created_time")
    } yield FacebookAlbum(id, name, createdTime)
  }

  implicit val decodePhotoId: Decoder[FacebookPhotoId] = decodeString.map(FacebookPhotoId)

  implicit val decodePhoto: Decoder[FacebookPhoto] = new Decoder[FacebookPhoto] {
    override def apply(c: HCursor) = for {
      id          <- c.get[FacebookPhotoId]("id")
      createdTime <- c.get[Option[Instant]]("created_time")
      images      <- c.get[List[FacebookImage]]("images")
      album       <- c.get[Option[FacebookAlbum]]("album")
    } yield FacebookPhoto(id, createdTime, images, album)
  }

}
