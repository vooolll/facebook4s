package domain.likes

import domain.profile.FacebookUserId

final case class FacebookLikes(
  likes   : List[FacebookLike],
  paging  : FacebookLikesPaging,
  summary : Option[FacebookLikesSummary] = None)

final case class FacebookLike(id: FacebookUserId, name: Option[String])

final case class FacebookLikesPaging(before: Option[String], after: Option[String])

final case class FacebookLikesSummary(totalCount: Int, canLike: Boolean, hasLikes: Boolean)