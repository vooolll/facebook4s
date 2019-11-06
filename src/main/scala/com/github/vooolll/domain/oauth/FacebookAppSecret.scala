package com.github.vooolll.domain.oauth

/**
  * Facebook application secret that can be obtained at https://developers.facebook.com/apps/your_app_id/settings/
  *
  * @param value value that represents secret - 32 symbols
  */
final case class FacebookAppSecret(value: String) extends AnyVal
