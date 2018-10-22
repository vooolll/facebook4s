package com.github.vooolll.domain.profile

import com.github.vooolll.domain.oauth.FacebookAppId

trait FacebookTagProfile

trait FacebookProfile extends FacebookTagProfile

case class FacebookProfileId(value: String) extends AnyVal {
  def asAppId  = FacebookAppId(value)
  def asUserId = FacebookUserId(value)
}