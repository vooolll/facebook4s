package com.github.vooolll.domain.profile

import java.net.URL

import com.github.vooolll.domain.FacebookPageId

/**
  * @param value Facebook user id
  */
final case class FacebookUserId(value: String) extends AnyVal with FacebookPageId

/**
  * @param id user numeric string
  * @param email user email
  * @param name user name
  * @param picture optional picture
  * @param firstName user first name
  * @param lastName user last name
  * @param link user page link
  * @param gender user gender
  * @param ageRange age segment for this person expressed as a minimum and maximum age
  * @param hometown hometown
  * @param location current town
  */
case class FacebookUser(
  id        : FacebookUserId,
  email     : Option[String],
  name      : Option[String],
  picture   : Option[FacebookUserPicture],
  firstName : Option[String],
  lastName  : Option[String],
  link      : Option[URL],
  gender    : Option[Gender],
  ageRange  : Option[AgeRange],
  hometown  : Option[FacebookTown],
  location  : Option[FacebookTown]) extends FacebookProfile

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
  source  : URL)

/**
  * @param id town id
  * @param name name like New York, USA
  */
case class FacebookTown(
  id: String,
  name: String)

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
