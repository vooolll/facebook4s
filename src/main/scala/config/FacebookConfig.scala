package config

import com.typesafe.config._
import domain.FacebookVersion

import scala.util.Properties

object FacebookConfig extends ConfigurationDetector {

  val config = ConfigFactory.load

  val version = FacebookVersion("2.10")
  val clientId = envVarOrConfig("FACEBOOK_CLIENT_ID", "facebook.clientId")
  val redirectUri = envVarOrConfig("FACEBOOK_REDIRECT_URI", "facebook.redirectUri")
  val appSecret = envVarOrConfig("FACEBOOK_APP_SECRET", "facebook.appSecret")
}

trait ConfigurationDetector {

  def config: Config

  def envVarOrConfig(envVar: String, configName: String): String =
    try {
      environmentVariable(envVar) getOrElse configuration(configName)
    } catch {
      case _: Throwable =>
        val msg = s"[facebook4s] configuration missing: Environment variable $envVar or configuration $configName not found."
        throw new RuntimeException(msg)
    }

  def environmentVariable(name: String): Option[String] = Properties.envOrNone(name)

  def configuration(path: String): String = config.getString(path)

}