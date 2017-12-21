package domain.likes

import domain.profile.FacebookUserId

final case class FacebookLike(id: FacebookUserId, name: Option[String])

final case class FacebookLikesPaging(before: Option[String], after: Option[String])