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
  link        : Option[URL],
  @Deprecated
  verified    : Option[Boolean],
  @Deprecated
  locale      : Option[Locale],
  @Deprecated
  timezone    : Option[ZoneOffset],
  gender      : Option[Gender],
  ageRange    : Option[AgeRange],
  @Deprecated
  cover       : Option[Cover],
  @Deprecated
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
