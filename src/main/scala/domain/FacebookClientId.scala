package domain

import cats.Show
import FacebookClientId._

case class FacebookClientId(value: String) extends FixedSizeValue(length, message)

object FacebookClientId {
  val length = 16

  val message = "Wrong facebook client id length"
}

object FacebookClientIdOps {
  implicit val showFacebookClientId = Show.show[FacebookClientId](_.value)
}
