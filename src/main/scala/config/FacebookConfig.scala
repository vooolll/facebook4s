package config

import com.typesafe.config._

import scala.util.Properties

object FacebookConfig extends ConfigurationDetector {

  val config = ConfigFactory.load

  val clientId = envVarOrConfig("FACEBOOK_CLIENT_ID", "facebook.client_id")
  val redirectUri = envVarOrConfig("FACEBOOK_REDIRECT_URI", "facebook.redirect_uri")
}

trait ConfigurationDetector {

  def config: Config

  protected def envVarOrConfig(envVar: String, configName: String): String =
    try {
      environmentVariable(envVar) getOrElse configuration(configName)
    } catch {
      case _: Throwable =>
        val msg = s"[facebook4s] configuration missing: Environment variable $envVar or configuration $configName not found."
        throw new RuntimeException(msg)
    }

  protected def environmentVariable(name: String): Option[String] = Properties.envOrNone(name)

  protected def configuration(path: String): String = config.getString(path)
}