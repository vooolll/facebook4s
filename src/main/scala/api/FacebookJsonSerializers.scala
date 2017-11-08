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

  implicit val facebookTokenTypeReads = new Reads[AppAccessToken] {
    implicit val facebookAppTokenReads = Json.reads[AppAccessToken]
    override def reads(json: JsValue) = json match {
      case JsString(any) => Json.fromJson[AppAccessToken](Json.obj("oauthTokenType" -> any))
      case _             => JsError(s"Unexpected JSON value $json")
    }
  }

  implicit val facebookTokenReads = new Reads[TokenValue] {
    implicit val localToken = Json.reads[TokenValue]
    override def reads(json: JsValue) = json match {
      case JsString(any) => Json.fromJson[TokenValue](Json.obj("value" -> any.split("\\|").last))
      case _             => JsError(s"Unexpected JSON value $json")
    }
  }

  implicit val facebookAppAccessTokenReads: Reads[FacebookAccessToken] = (
    (JsPath \ "access_token").read[TokenValue] and
      (JsPath \ "token_type").read[AppAccessToken]
    )(FacebookAccessToken.apply _)

  implicit val facebookClientCodeReads: Reads[FacebookClientCode] = (
    (JsPath \ "code").read[String] and
      (JsPath \ "machine_id").readNullable[String]
    )(FacebookClientCode.apply _)

  implicit val facebookUserAccessTokenReads = new Reads[FacebookAccessToken] {
    override def reads(json: JsValue) = {
      json match {
        case obj: JsObject =>
          val tokenValueRaw = (obj \ "access_token").as[String]
          val tokenExpiresIn = (obj \ "expires_in").as[Int]
          val oauthTokenType = (obj \ "token_type").as[String]
          JsSuccess(FacebookAccessToken(
            TokenValue(tokenValueRaw),UserAccessToken(oauthTokenType, tokenExpiresIn.seconds)))
        case _             => JsError(s"Unexpected JSON value $json")
      }
    }
  }

  implicit val facebookPagingReads: Reads[FacebookPaging] = Json.reads[FacebookPaging]

  val facebookInstant = new Reads[Instant] {
    override def reads(json: JsValue) = json match {
      case JsString(any) => JsSuccess(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(any).toInstant)
      case _             => JsError(s"Unexpected JSON value $json")
    }
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
