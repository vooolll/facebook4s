package domain.comments

import java.time.Instant

import domain.FacebookOrder
import domain.likes.FacebookPaging
import domain.media.FacebookAttachment
import domain.profile.FacebookProfileId

final case class FacebookCommentId(value: String)

final case class FacebookComment(
  id          : FacebookCommentId,
  message     : Option[String],
  createdTime : Option[Instant],
  from        : Option[FacebookProfileId],
  parent      : Option[FacebookComment],
  attachment  : Option[FacebookAttachment])

final case class FacebookComments(
  comments : List[FacebookComment],
  paging   : Option[FacebookPaging],
  summary  : Option[FacebookCommentSummary] = None)

final case class FacebookCommentSummary(
  order: FacebookOrder,
  totalCount: Int,
  canComment: Option[Boolean])
