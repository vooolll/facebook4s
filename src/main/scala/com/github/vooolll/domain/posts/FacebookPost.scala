package com.github.vooolll.domain.posts

import java.net.URL
import java.time.Instant

import com.github.vooolll.domain.media.FacebookAttachment
import com.github.vooolll.domain.profile.FacebookProfileId

final case class FacebookPostId(value: String) extends AnyVal

final case class FacebookPost(
  id: FacebookPostId,
  name: Option[String],
  message: Option[String],
  createdTime: Option[Instant],
  objectId: Option[String], // TODO facebook4s-133: change type to MediaObjectId
  picture: Option[URL],
  from: Option[FacebookProfileId],
  attachments: List[FacebookAttachment]
)
