package domain

import cats.Show

object FacebookShowOps {
  implicit val showFacebookVersion = Show.show[FacebookVersion](fbVersion => "v" + fbVersion.value)
  implicit val showFacebookClientId = Show.show[FacebookClientId](_.value)
  implicit val showFacebookAppId = Show.show[FacebookAppId](_.value)
  implicit val showFacebookAppSecret = Show.show[FacebookAppSecret](_.value)
  implicit val showFacebookAccessToken = Show.show[FacebookAccessToken](_.valueToken.value)

}
