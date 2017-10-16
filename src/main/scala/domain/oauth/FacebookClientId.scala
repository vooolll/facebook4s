package domain.oauth

import FacebookClientId._

/**
  * Facebook client id, it is also called app id
  * @param value facebook application id
  */
final case class FacebookClientId(value: String) extends FixedSizeValue(length, message)

/**
  * Facebook app id, it is also called client id
  * @param value facebook application id(fixed size value - 16)
  */
final case class FacebookAppId(value: String) extends FixedSizeValue(length, message)

object FacebookClientId {
  val length = 16

  val message = "Wrong facebook client id length"
}