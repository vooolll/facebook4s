package com.github.vooolll.domain.profile

import java.net.URL

import com.github.vooolll.domain.oauth.FacebookApplicationId

final case class FacebookApplication(id: FacebookApplicationId, link: URL, name: String) extends FacebookProfile
