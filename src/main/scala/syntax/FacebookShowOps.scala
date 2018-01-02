package syntax

import cats.Show
import domain.{FacebookVersion, FacebookAttribute}
import domain.oauth._
import domain.permission.FacebookPermissions.FacebookPermission
import domain.posts.FacebookPostId
import domain.profile.FacebookUserId

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
  implicit val showFacebookPermissions = Show.show[FacebookPermission](_.value)
  implicit val showFacebookApplicationId = Show.show[FacebookApplicationId](_.value)
  implicit val showHasStringValue = Show.show[FacebookAttribute](_.value)
  implicit val showFacebookPostId = Show.show[FacebookPostId](_.value)
}
