package domain.profile

/**
  * @param value Facebook user id
  */
final case class FacebookUserId(value: String)

/**
  * @param id facebook user id - numeric string
  * @param name facebook user first name
  */
case class FacebookUser(id: FacebookUserId, name: String) extends FacebookProfile
