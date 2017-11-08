package api

import java.text.SimpleDateFormat
import java.time.Instant

import domain._
import domain.feed.{FacebookPaging, FacebookPost, FacebookSimplePost, FacebookUserFeed}
import domain.oauth._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

import scala.concurrent.duration.DurationInt

/**
  * Play json serializers
  */
object FacebookJsonSerializers {

  implicit val facebookTokenTypeReads: Reads[AppAccessToken] = __.read[String].map(AppAccessToken)

  implicit val facebookTokenReads: Reads[TokenValue] = __.read[String].map(TokenValue.fromRaw)

  implicit val facebookAppAccessTokenReads: Reads[FacebookAccessToken] = (
    (JsPath \ "access_token").read[TokenValue] and
      (JsPath \ "token_type").read[AppAccessToken]
    )(FacebookAccessToken.apply _)

  implicit val facebookClientCodeReads: Reads[FacebookClientCode] = (
    (JsPath \ "code").read[String] and
      (JsPath \ "machine_id").readNullable[String]
    )(FacebookClientCode.apply _)

  implicit val facebookUserAccessTokenReads: Reads[FacebookAccessToken] = (
    (__ \ "access_token").read[String] and
    (__ \ "expires_in").read[Int] and
    (__ \ "token_type").read[String]
  ) { (tokenValueRaw, tokenExpiresIn, oauthTokenType) =>
    FacebookAccessToken(
      TokenValue(tokenValueRaw),UserAccessToken(oauthTokenType, tokenExpiresIn.seconds))
  }

  // Avoid mixing of DSL-based and macros-based formats
  implicit val facebookPagingReads: Reads[FacebookPaging] = Json.reads[FacebookPaging]

  val facebookInstant: Reads[Instant] = Reads[Instant] {
    case JsString(any) => JsSuccess(DateTimeFormatter.ISO_DATE_TIME.parse(any, Instant.from _))
    case json          => JsError(s"Unexpected JSON value $json")
  }

  implicit val facebookPostReads: Reads[FacebookSimplePost] = (
    (JsPath \ "id").read[String] and
      (JsPath \ "story").read[String] and
      (JsPath \ "created_time").read[Instant](facebookInstant)
    )(FacebookSimplePost.apply _)

  implicit val facebookFeedReads: Reads[FacebookUserFeed] = (
    (JsPath \ "data").read[List[FacebookSimplePost]] and
      (JsPath \ "paging").read[FacebookPaging]
    )(FacebookUserFeed.apply _)

  implicit val facebookAppIdReads = new Reads[FacebookAppId] {
    override def reads(json: JsValue) = json match {
      case JsString(any) => Json.fromJson[FacebookAppId](Json.obj("value" -> any))(Json.reads[FacebookAppId])
      case _             => JsError(s"Unexpected JSON value $json")
    }
  }


  implicit val facebookUserIdReads = new Reads[FacebookUserId] {
    override def reads(json: JsValue) = json match {
      case JsString(any) => Json.fromJson[FacebookUserId](Json.obj("value" -> any))(Json.reads[FacebookUserId])
      case _ => JsError(s"Unexpected JSON value $json")
    }
  }

  implicit val facebookErrorReads = Json.reads[FacebookError]
  implicit val facebookLoginErrorReads = Json.reads[FacebookOauthError]

}
