package com.github.vooolll.base

import com.github.vooolll.config.FacebookConfig
import com.github.vooolll.services.FacebookUrls

object TestUrls extends FacebookUrls {
  override val clientId  = FacebookConfig.clientId
  override val appSecret = FacebookConfig.appSecret
}
