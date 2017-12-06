package domain.profile

import domain.oauth.FacebookAppId

trait FacebookTagProfile

trait FacebookProfile extends FacebookTagProfile

case class FacebookProfileId(value: String) {
  def asAppId  = FacebookAppId(value)
  def asUserId = FacebookUserId(value)
}