package domain

import org.f100ded.scalaurlbuilder.URLBuilder

case class FacebookRedirectUri(uri: String) {
  URLBuilder(uri)
}
