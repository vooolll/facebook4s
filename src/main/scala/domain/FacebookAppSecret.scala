package domain

import domain.FacebookAppSecret._

/**
  * Facebook application secret that can be obtained at https://developers.facebook.com/apps/your_app_id/settings/
  * @param value value that represents secret - 32 symbols
  */
final case class FacebookAppSecret(value: String) extends FixedSizeValue(length, message)

/**
  * Companion object for FacebookAppSecret
  */
object FacebookAppSecret {
  val length = 32

  val message = "Wrong facebook app secret length"
}