package com.github.vooolll.domain.comments

import java.time.Instant

import com.github.vooolll.domain.media.FacebookAttachment
import com.github.vooolll.domain.profile.FacebookProfileId
import com.github.vooolll.domain.{FacebookOrder, FacebookPaging}

final case class FacebookCommentId(value: String) extends AnyVal

final case class FacebookComment(
  id          : FacebookCommentId,
  message     : Option[String],
  createdTime : Option[Instant],
  from        : Option[FacebookProfileId],
  parent      : Option[FacebookComment],
  mediaObject : Option[FacebookMediaObject],
  attachment  : Option[FacebookAttachment])

final case class FacebookComments(
  comments : List[FacebookComment],
  paging   : Option[FacebookPaging],
  summary  : Option[FacebookCommentSummary] = None)

final case class FacebookCommentSummary(
  order: FacebookOrder,
  totalCount: Int,
  canComment: Option[Boolean])

final case class FacebookMediaObjectId(value: String)

final case class FacebookMediaObject(id: FacebookMediaObjectId, createdTime: Instant)