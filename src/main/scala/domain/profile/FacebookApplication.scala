package domain.profile

import domain.oauth.FacebookApplicationId

final case class FacebookApplication(
  id   : FacebookApplicationId,
  link : String,
  name : String) extends FacebookProfile