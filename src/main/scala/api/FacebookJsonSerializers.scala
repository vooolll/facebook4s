package api

import domain._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

import scala.concurrent.duration.DurationInt

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
  implicit val facebookLoginErrorReads = Json.reads[FacebookTokenError]

}
