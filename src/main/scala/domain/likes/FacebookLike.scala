package domain.likes

final case class FacebookLike(id: FacebookLikeId, name: Option[String])
final case class FacebookLikeId(value: String)

final case class FacebookLikesPaging(before: Option[String], after: Option[String])