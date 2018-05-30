package com.github.vooolll.client

import com.github.vooolll.base.AsyncSpec
import com.github.vooolll.domain.oauth._

class AppAccessTokenSpec extends AsyncSpec with FacebookClientFixture {

  "Facebook Graph Api" should {
    "return access token and token type" in {
      facebookClient.appAccessTokenResult() map {
        case Right(appAccessToken) =>
          appAccessToken.tokenType shouldBe AppAccessToken("bearer")
          appAccessToken.tokenValue.value.length() shouldBe 44
        case Left(_) => fail("left unexpected in right credentials")
      }
    }

    "return facebook error with on invalid secret either" in {
      facebookWrongClientSecret.appAccessTokenResult() map {
        case Right(_) => fail("right unexpected in wrong secret")
        case Left(facebookLoginError) => facebookLoginError shouldBe FacebookError("Error validating client secret.",
          FacebookError.Unknown)
      }
    }

    "return facebook error with on unknown secret and clientId either" in {
      facebookWrongClientIdAndSecret.appAccessTokenResult() map {
        case Right(_) => fail("right unexpected in wrong secret")
        case Left(facebookLoginError) => facebookLoginError shouldBe FacebookError("An unexpected error has occurred. Please retry your request later.",
          FacebookError.ServiceDown)
      }
    }

    "return facebook error on exception" in {
      recoverToSucceededIf[RuntimeException] {
        facebookWrongClientSecret.appAccessToken()
      }
    }
  }

}

trait FacebookClientFixture {
  val facebookWrongClientSecret = FacebookClient(
    clientId = FacebookClientId("1969406143275709"),
    appSecret = FacebookAppSecret("41725f9990f489d5f1b1533a77a17263"))

  val facebookWrongClientIdAndSecret = FacebookClient(
    clientId = FacebookClientId("1234567143275712"),
    appSecret = FacebookAppSecret("41725f9990f489d5f1b1533a77a17263"))

  val facebookClient = FacebookClient()
}
