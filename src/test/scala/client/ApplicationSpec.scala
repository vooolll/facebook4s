package client

import cats.syntax.either._
import config.TestConfiguration._
import domain.oauth.{FacebookAppId, FacebookClientId}
import domain.profile.FacebookApplication

class ApplicationSpec extends FacebookClientSupport {

  val appId = FacebookAppId("1970529913214515")
  val clientId = FacebookClientId(appId.value)
  val application = FacebookApplication(appId, "http://localhost:9000/redirect", "testing_app")

  "Facebook Graph Api" should {
    "get application by id" in { c =>
      c.application(appId, userTokenRaw) map(_ shouldBe application)
    }

    "get application result by id" in { c =>
      c.applicationResult(clientId, userTokenRaw) map(_ shouldBe application.asRight)
    }
  }
}
