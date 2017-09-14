package api

import domain.AppAccessToken
import org.scalatest.{AsyncWordSpec, Matchers}

class AppAccessTokenSpec extends AsyncWordSpec with Matchers {
  "Facebook Graph Api" should {
    "return access token and token type" in {
      val facebookClient = new FacebookClient()
      facebookClient.appAccessToken map { appAccessToken =>
        appAccessToken.tokenType shouldBe AppAccessToken("bearer")
        appAccessToken.valueToken.value.length() shouldBe 27
      }
    }
  }
}
