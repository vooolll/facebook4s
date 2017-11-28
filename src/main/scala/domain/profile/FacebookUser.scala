package domain.profile

import java.time.{Instant, ZoneOffset}
import java.util.Locale

/**
  * @param value Facebook user id
  */
final case class FacebookUserId(value: String)

/**
  * @param id id - numeric string
  * @param name optional name
  * @param picture optional picture
  * @param locale optional locale
  * @param firstName optional first name
  * @param lastName optional last name
  * @param link optional link
  * @param verified optional verified status flag
  * @param gender optional gender
  * @param timezone optional person's current timezone offset from UTC
  * @param ageRange optional age segment for this person expressed as a minimum and maximum age
  * @param cover optional cover
  * @param updatedTime optional updated time
  */
case class FacebookUser(
  id          : FacebookUserId,
  name        : Option[String],
  picture     : Option[FacebookUserPicture],
  firstName   : Option[String],
  lastName    : Option[String],
  link        : Option[String],
  verified    : Option[Boolean],
  locale      : Option[Locale],
  timezone    : Option[ZoneOffset],
  gender      : Option[Gender],
  ageRange    : Option[AgeRange],
  cover       : Option[Cover],
  updatedTime : Option[Instant]) extends FacebookProfile

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

/**
  * @param id cover id
  * @param offsetX x offset
  * @param offsetY y offset
  * @param source source link
  */
case class Cover(
  id      : String,
  offsetX : Double,
  offsetY : Double,
  source  : String
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
