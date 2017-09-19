package api

import domain._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

object FacebookJsonSerializers {
  implicit val facebookAppAccessTokenReads = Json.reads[AppAccessToken]

  implicit val facebookTokenTypeReads = new Reads[FacebookTokenType] {
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

  implicit val facebookAccessTokenReads: Reads[FacebookAccessToken] = (
    (JsPath \ "access_token").read[TokenValue] and
      (JsPath \ "token_type").read[FacebookTokenType]
    )(FacebookAccessToken.apply _)

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
