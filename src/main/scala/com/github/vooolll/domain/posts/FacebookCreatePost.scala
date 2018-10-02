package com.github.vooolll.domain.posts

import java.net.URL

import com.github.vooolll.domain.oauth.FacebookAccessToken
import io.circe.Json


case class FacebookCreatePost(
  message      : String,
  link         : Option[URL],
  name         : Option[String],
  description  : Option[String],
  caption      : Option[String],
  picture      : Option[URL],
  published    : Option[Boolean],
  callToAction : Option[Json],
  accessToken  : FacebookAccessToken,
  thumbnail    : Option[URL]) {

  def toMap = {
    val mandatoryPart = Map(
      "message"      -> message,
      "access_token" -> accessToken.tokenValue.value
    )

    val optionalPart = {
      link.map("link" -> _.toString) ++ name.map("name" -> _) ++ description.map("description" -> _)
    } ++ {
      caption.map("caption" -> _) ++ picture.map("picture" -> _.toString) ++ published.map("published" -> _.toString)
    } ++ {
      callToAction.map("call_to_action" -> _.toString()) ++ thumbnail.map("thumbnail" -> _.toString)
    }
    mandatoryPart ++ optionalPart
  }
}