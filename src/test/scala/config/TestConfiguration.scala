package config

import com.typesafe.config.ConfigFactory
import domain.{AppAccessToken, FacebookAccessToken, TokenValue}

object TestConfiguration extends ConfigurationDetector {

  override def config = ConfigFactory.load

  val appAccessToken = FacebookAccessToken(
    TokenValue(envVarOrConfig("FACEBOOK_TEST_APP_ACCESS_TOKEN", "facebook.testAppAccessToken")), AppAccessToken("bearer"))
}
