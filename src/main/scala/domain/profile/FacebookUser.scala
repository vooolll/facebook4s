package domain.profile

import java.net.URL
import java.time.{Instant, ZoneOffset}
import java.util.Locale

/**
  * @param value Facebook user id
  */
final case class FacebookUserId(value: String)

/**
  * @param id id - numeric string
  * @param email email - optional email
  * @param name optional name
  * @param picture optional picture
  * @param firstName optional first name
  * @param lastName optional last name
  * @param link optional link
  * @param gender optional gender
  * @param ageRange optional age segment for this person expressed as a minimum and maximum age
  */
case class FacebookUser(
  id          : FacebookUserId,
  email       : Option[String],
  name        : Option[String],
  picture     : Option[FacebookUserPicture],
  firstName   : Option[String],
  lastName    : Option[String],
  link        : Option[URL],
  gender      : Option[Gender],
  ageRange    : Option[AgeRange]) extends FacebookProfile

/**
  * @param height image height
  * @param isSilhouette is silhouette
  * @param url link
  * @param wight image wight
  */
case class FacebookUserPicture(
  height       : Double,
  isSilhouette : Boolean,
  url          : URL,
  wight        : Double)

/**
  * @param id cover id
  * @param offsetX x offset
  * @param offsetY y offset
  * @param source source link
  */
case class Cover(
  id      : Option[String],
  offsetX : Double,
  offsetY : Double,
  source  : URL
)

/**
  * @param min upper bounds of the range for this person's age
  * @param max lower bounds of the range for this person's age
  */
case class AgeRange(min: Int, max: Option[Int])

trait Gender

object Gender {
  case object Male extends Gender
  case object Female extends Gender
}
