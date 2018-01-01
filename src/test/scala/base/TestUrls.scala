package base

import config.FacebookConfig
import services.FacebookUrls

object TestUrls extends FacebookUrls {
  override val clientId = FacebookConfig.clientId
  override val appSecret = FacebookConfig.appSecret
}
