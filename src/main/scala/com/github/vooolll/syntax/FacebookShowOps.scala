package com.github.vooolll.syntax

import cats.Show
import com.github.vooolll.domain.albums.photo.FacebookPhotoId
import com.github.vooolll.domain.{FacebookAttribute, FacebookVersion}
import com.github.vooolll.domain.oauth._
import com.github.vooolll.domain.permission.FacebookPermissions.FacebookPermission
import com.github.vooolll.domain.posts.FacebookPostId
import com.github.vooolll.domain.profile.FacebookUserId

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
  implicit val showFacebookPhotoId = Show.show[FacebookPhotoId](_.value)
}
