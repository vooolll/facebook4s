package com.github.vooolll.config

import com.typesafe.config.{Config, ConfigException}
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import cats.implicits._

class ConfigurationDetectorSpec extends WordSpec with Matchers with MockitoSugar {

  val testEnvVarValue = "configuration-from-env-var"
  val testConfigValue = "configuration-from-file"

  "ConfigurationDetector" should {
    "return config value if no env variable" in {
      new Scope with NoEnvVariable with ConfigFromFile {
        envVarOrConfig(variableName, configName) shouldBe testConfigValue
      }
    }

    "return env variable value if it is defined" in {
      new Scope with ConfigFromEnv with ConfigFromFile {
        envVarOrConfig(variableName, configName) shouldBe testEnvVarValue
      }
    }

    "return throw exception if both config sources undefined" in {
      new Scope with NoEnvVariable with NoConfigFromFile {
        an[RuntimeException] shouldBe thrownBy(envVarOrConfig(variableName, configName))
      }
    }

    "return option none if no config" in {
      new Scope with NoEnvVariable with NoConfigFromFile {
        envVarOrConfigOptional(variableName, configName) shouldBe None
      }
    }
  }

  abstract class Scope {
    val config = mock[Config]

    val variableName = "MY-CONFIG"
    val configName = "my.config"
  }

  trait NoEnvVariable extends ConfigurationDetector {
    override def environmentVariable(name: String) = None
  }

  trait ConfigFromFile extends ConfigurationDetector {
    override def configuration(path: String) = testConfigValue
  }

  trait ConfigFromEnv extends ConfigurationDetector {
    override def environmentVariable(name: String) = testEnvVarValue.some
  }

  trait NoConfigFromFile extends ConfigurationDetector {
    override def configuration(path: String) = throw new ConfigException.Missing(path)
  }
}