package client

import domain.oauth.UserAccessToken
import org.scalatest.Matchers

import scala.concurrent.duration.DurationInt

class UserAccessTokenSpec extends FacebookClientSpec {

  "Facebook Graph Api" should {
    "return user access token" in { c =>
      c.mockSendWithResource(resourcePath = "testdata/user_access_token.json")
      c.userAccessToken("code") map { token =>
        token.tokenValue.value shouldBe "test token"
        token.tokenType shouldBe UserAccessToken("bearer", 5107587.seconds)
      }
    }

    "return error in wrong code" in { c =>
      c.mockSendError(resourcePath = "testdata/user_access_token_wrong_code.json")
      recoverToSucceededIf[RuntimeException] {
        c.userAccessToken("code")
      }
    }

    "return prolonged user access token" in { c =>
      c.mockSendWithResource(resourcePath = "testdata/long_lived_access_token.json")
      c.extendUserAccessToken("short lived token") map { token =>
        token.tokenValue.value shouldBe "long lived token"
        token.tokenType shouldBe UserAccessToken("bearer", 5184000.seconds)
      }
    }
  }

}