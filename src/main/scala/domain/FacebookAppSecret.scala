package domain

import cats.Show
import FacebookAppSecret._

case class FacebookAppSecret(value: String) extends FixedSizeValue(length, message)

object FacebookAppSecret {
  val length = 32

  val message = "Wrong facebook app secret length"
}

object FacebookAppSecretOps {
  implicit val facebookAppSecret = Show.show[FacebookAppSecret](_.value)
}