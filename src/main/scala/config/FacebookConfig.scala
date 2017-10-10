package config

import com.typesafe.config._
import domain._

import scala.util.{Failure, Properties, Success, Try}
import scala.util.control.NonFatal

object FacebookConfig extends ConfigurationDetector {

  val config = ConfigFactory.load

  val version = FacebookVersion("2.10")
  val clientId = FacebookClientId(envVarOrConfig("FACEBOOK_CLIENT_ID", "facebook.clientId"))
  val redirectUri = FacebookRedirectUri(envVarOrConfig("FACEBOOK_REDIRECT_URI", "facebook.redirectUri"))
  val appSecret = FacebookAppSecret(envVarOrConfig("FACEBOOK_APP_SECRET", "facebook.appSecret"))
}

trait ConfigurationDetector {

  def config: Config

  def envVarOrConfig(envVar: String, configName: String): String = {
    Try(environmentVariable(envVar) getOrElse configuration(configName)) match {
      case Success(s)        => s
      case Failure(e) =>
        val msg = s"[facebook4s] configuration missing: " +
          s"Environment variable $envVar or configuration $configName not found."
        throw new RuntimeException(msg)
    }
  }

  def environmentVariable(name: String): Option[String] = Properties.envOrNone(name)

  def configuration(path: String): String = config.getString(path)

}