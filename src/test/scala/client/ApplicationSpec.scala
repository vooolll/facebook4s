package client

import cats.syntax.either._
import serialization.compatibility.TestEntities._

class ApplicationSpec extends FacebookClientSupport {

  "Facebook Graph Api" should {
    "get application by id" in { c =>
      c.mockSendWithResource(resourcePath = "testdata/basic_application.json")
      c.application(appId, userAccessToken) map(_ shouldBe application)
      c.application(appId, userAccessToken.tokenValue.value) map(_ shouldBe application)
    }

    "get application result by id" in { c =>
      c.mockSendWithResource(resourcePath = "testdata/basic_application.json")
      c.applicationResult(facebookClientId, userAccessToken) map(_ shouldBe application.asRight)
    }
  }
}
