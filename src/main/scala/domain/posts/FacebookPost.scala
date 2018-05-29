package domain.posts

import java.net.URL
import java.time.Instant

import domain.profile.FacebookProfileId

final case class FacebookPostId(value: String)

final case class FacebookPost(
  id          : FacebookPostId,
  message     : Option[String],
  createdTime : Option[Instant],
  objectId    : Option[String], // TODO: change type to MediaObjectId
  picture     : Option[URL],
  from        : Option[FacebookProfileId])
