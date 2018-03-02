package domain.profile

import java.net.URL

import domain.oauth.FacebookApplicationId

final case class FacebookApplication(
  id   : FacebookApplicationId,
  link : URL,
  name : String) extends FacebookProfile