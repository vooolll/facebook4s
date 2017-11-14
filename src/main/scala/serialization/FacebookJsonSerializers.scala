package serialization

import java.time._
import java.time.format._

import domain._
import domain.feed._
import domain.oauth._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

import scala.concurrent.duration._

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
    FacebookAccessToken(TokenValue(tokenValueRaw), UserAccessToken(oauthTokenType, tokenExpiresIn.seconds))
  }

  implicit val facebookPagingReads: Reads[FacebookPaging] = Json.reads[FacebookPaging]

  val facebookInstant: Reads[Instant] = Reads[Instant] {
    case JsString(any) => JsSuccess(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ").parse(any, Instant.from _))
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

  implicit val facebookAppIdReads =  __.read[String].map(FacebookAppId)
  implicit val facebookUserIdReads = __.read[String].map(FacebookUserId)

  implicit val facebookErrorReads = Json.reads[FacebookError]
  implicit val facebookLoginErrorReads = Json.reads[FacebookOauthError]

}
