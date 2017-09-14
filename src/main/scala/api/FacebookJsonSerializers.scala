package api

import domain.{FacebookAccessToken, AppAccessToken, TokenValue, FacebookTokenType}
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._


object FacebookJsonSerializers {
  implicit val facebookAppAccessToken = Json.reads[AppAccessToken]
  implicit val tokenType = new Reads[FacebookTokenType] {
    override def reads(json: JsValue) = json match {
      case JsString(any) => Json.fromJson[AppAccessToken](Json.obj("tokenType" -> any))
      case _             => JsError(s"Unexpected JSON value $json")
    }
  }

  implicit val token = new Reads[TokenValue] {
    implicit val localToken = Json.reads[TokenValue]
    override def reads(json: JsValue) = json match {
      case JsString(any) => Json.fromJson[TokenValue](Json.obj("value" -> any.split("\\|").last))
      case _             => JsError(s"Unexpected JSON value $json")
    }
  }

  implicit val facebookAccessToken: Reads[FacebookAccessToken] = (
    (JsPath \ "access_token").read[TokenValue] and
      (JsPath \ "token_type").read[FacebookTokenType]
    )(FacebookAccessToken.apply _)
}
