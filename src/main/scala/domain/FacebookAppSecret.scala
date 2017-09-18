package domain

import domain.FacebookAppSecret._

final case class FacebookAppSecret(value: String) extends FixedSizeValue(length, message)

object FacebookAppSecret {
  val length = 32

  val message = "Wrong facebook app secret length"
}