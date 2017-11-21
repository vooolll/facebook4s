package client

import cats.syntax.either._
import serialization.compatibility.TestEntities

class ApplicationSpec extends FacebookClientSupport {

  "Facebook Graph Api" should {
    "get application by id" in { c =>
      c.mockSendWithResource(resourcePath = "testdata/application.json")
      c.application("token", TestEntities.userAccessToken) map(
        _ shouldBe TestEntities.application)
    }

    "get application result by id" in { c =>
      c.mockSendWithResource(resourcePath = "testdata/application.json")
      c.applicationResult("token", TestEntities.userAccessToken) map(
        _ shouldBe TestEntities.application.asRight)
    }
  }
}
