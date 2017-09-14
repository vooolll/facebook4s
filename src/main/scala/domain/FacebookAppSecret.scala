package domain

import cats.Show

case class FacebookAppSecret(value: String) extends FixedSizeValue(FacebookAppSecret.length)

object FacebookAppSecret {
  val length = 32
}

object FacebookAppSecretOps {
  implicit val facebookAppSecret = Show.show[FacebookAppSecret](facebookSecret => facebookSecret.value)
}