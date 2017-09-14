package domain

import cats.Show

case class FacebookClientId(value: String) extends FixedSizeValue(FacebookClientId.length)

object FacebookClientId {
  val length = 16
}

object FacebookClientIdOps {
  implicit val showFacebookClientId = Show.show[FacebookClientId](facebookClientId => facebookClientId.value)
}
