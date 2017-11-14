package domain.feed

sealed trait FacebookTagProfile

sealed trait FacebookProfile extends FacebookTagProfile

final case class FacebookUser(id: String) extends FacebookProfile

final case class FacebookPage(id: String) extends FacebookProfile

final case class FacebookGroup(id: String) extends FacebookProfile

final case class FacebookEvent(id: String) extends FacebookProfile