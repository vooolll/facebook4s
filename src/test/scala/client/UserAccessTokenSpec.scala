package client

import base._
import base.FacebookClientStubSupport
import domain.oauth.UserAccessToken

import scala.concurrent.duration.DurationInt

class UserAccessTokenSpec extends FacebookClientStubSupport {

  "Facebook Graph Api" should {
    "return user access token" in { c =>
      c.mockSendWithResource(resourcePath = "testdata/user_access_token.json")
      c.userAccessToken("code") map (_ shouldBe userAccessToken)
    }

    "return error in wrong code" in { c =>
      c.mockSendError(resourcePath = "testdata/oauth_error.json")
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