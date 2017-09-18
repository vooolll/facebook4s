package domain

import org.f100ded.scalaurlbuilder.URLBuilder

final case class FacebookRedirectUri(uri: String) {
  URLBuilder(uri)
}
