package client

import serialization.compatibility.TestEntities._

class UserSpec extends FacebookClientSupport {
  "Facebook Graph Api" should {
    "return user profile" in { c =>
      c.mockSendWithResource(resourcePath = "testdata/basic_user.json")
      c.user(userId, userAccessToken) map (_ shouldBe user)
    }
  }
}
