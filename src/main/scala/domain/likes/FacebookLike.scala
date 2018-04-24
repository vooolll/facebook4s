package domain.likes

import domain.FacebookPaging
import domain.profile.FacebookUserId

final case class FacebookLikes(
  likes   : List[FacebookLike],
  paging  : Option[FacebookPaging],
  summary : Option[FacebookLikesSummary] = None)

final case class FacebookLike(id: FacebookUserId, name: Option[String])

final case class FacebookLikesSummary(totalCount: Int, canLike: Option[Boolean], hasLikes: Option[Boolean])