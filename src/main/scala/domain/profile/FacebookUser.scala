package domain.profile

/**
  * @param value Facebook user id
  */
final case class FacebookUserId(value: String)

/**
  * @param id facebook user id - numeric string
  * @param name facebook user first name
  * @param picture optional picture
  */
case class FacebookUser(
  id      : FacebookUserId,
  name    : Option[String],
  picture : Option[FacebookUserPicture]) extends FacebookProfile

/**
  * @param height image height
  * @param isSilhouette is silhouette
  * @param url link
  * @param wight image wight
  */
case class FacebookUserPicture(
  height       : Double,
  isSilhouette : Boolean,
  url          : String,
  wight        : Double)
