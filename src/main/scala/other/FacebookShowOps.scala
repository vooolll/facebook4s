package other

import cats.Show
import domain.{FacebookUserId, FacebookVersion}
import domain.oauth._

/**
  * Cats show implementation for domain
  */
object FacebookShowOps {
  implicit val showFacebookVersion = Show.show[FacebookVersion](fbVersion => "v" + fbVersion.value)
  implicit val showFacebookClientId = Show.show[FacebookClientId](_.value)
  implicit val showFacebookAppId = Show.show[FacebookAppId](_.value)
  implicit val showFacebookAppSecret = Show.show[FacebookAppSecret](_.value)
  implicit val showFacebookAccessToken = Show.show[FacebookAccessToken](_.tokenValue.value)
  implicit val showFacebookRedirectUri = Show.show[FacebookRedirectUri](_.uri)
  implicit val showFacebookUserId = Show.show[FacebookUserId](_.value)
}
