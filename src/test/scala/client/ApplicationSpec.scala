package client

import cats.syntax.either._
import config.TestConfiguration._
import domain.oauth.{FacebookAppId, FacebookClientId}
import domain.profile.FacebookApplication

class ApplicationSpec extends FacebookClientStubSupport {

  val appId = FacebookAppId("1969406143275709")
  val clientId = FacebookClientId("1969406143275709")
  val application = FacebookApplication(FacebookAppId("1969406143275709"),
    "https://www.facebook.com/games/?app_id=1969406143275709",
    "testing_app")

  "Facebook Graph Api" should {
    "get application by id" in { c =>
      c.application(appId, userTokenRaw) map(_ shouldBe application)
    }

    "get application result by id" in { c =>
      c.applicationResult(clientId, userTokenRaw) map(_ shouldBe application.asRight)
    }
  }
}
