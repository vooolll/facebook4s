package client


import config.TestConfiguration
import domain.profile._

class UserSpec extends FacebookClientSupport {
  "Facebook Graph Api" should {
    "return user profile" in { c =>
      c.userProfile(FacebookUserId("499283963749541"), TestConfiguration.userAccessToken) map (_ shouldBe realUser)
    }
  }
}
