package domain

import domain.FacebookClientId._

final case class FacebookClientId(value: String) extends FixedSizeValue(length, message)

final case class FacebookAppId(value: String) extends FixedSizeValue(length, message)

object FacebookClientId {
  val length = 16

  val message = "Wrong facebook client id length"
}