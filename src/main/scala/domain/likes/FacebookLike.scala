package domain.likes

final case class FacebookLike(id: FacebookLikeId, name: String)
final case class FacebookLikeId(value: String)