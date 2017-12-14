package config

import com.typesafe.config.ConfigFactory
import domain.oauth.{AppAccessToken, FacebookAccessToken, TokenValue, UserAccessToken}

import scala.concurrent.duration._

object TestConfiguration extends ConfigurationDetector {

  override def config = ConfigFactory.load

  val appAccessToken = FacebookAccessToken(
    tokenValue = tokenValueOf("FACEBOOK_TEST_APP_ACCESS_TOKEN", "facebook.testAppAccessToken"),
    tokenType  = AppAccessToken("bearer"))

  val userAccessToken = FacebookAccessToken(
    tokenValue = tokenValueOf("FACEBOOK_TEST_USER_ACCESS_TOKEN", "facebook.testUserAccessToken"),
    tokenType  = UserAccessToken("bearer", 60.days)
  )

  val userTokenRaw = userAccessToken.tokenValue.value

  def tokenValueOf(envStr: String, typesafeStr: String) =
    TokenValue(envVarOrConfig(envStr, typesafeStr))
}
