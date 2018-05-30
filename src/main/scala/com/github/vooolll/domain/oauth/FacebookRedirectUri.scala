package com.github.vooolll.domain.oauth

import org.f100ded.scalaurlbuilder.URLBuilder

/**
  * @param uri redirect uri can be obtained at https://developers.facebook.com/apps/your_app_id/settings/
  */
final case class FacebookRedirectUri(uri: String) {
  URLBuilder(uri)
}
