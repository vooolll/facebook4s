package api

import domain.{AppAccessToken, FacebookAppSecret, FacebookClientId}
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncWordSpec, Matchers}

class AppAccessTokenSpec extends AsyncWordSpec with Matchers with MockitoSugar with FacebookClientFixture {

  "Facebook Graph Api" should {
    "return access token and token type" in {
      facebookClient.appAccessTokenEither() map {
        case Right(appAccessToken) =>
          appAccessToken.tokenType shouldBe AppAccessToken("bearer")
          appAccessToken.tokenValue.value.length() shouldBe 27
        case Left(_) => fail("left unexpected in right credentials")
      }
    }

    "return facebook error with on invalid secret either" in {
      facebookWrongClientSecret.appAccessTokenEither() map {
        case Right(_) => fail("right unexpected in wrong secret")
        case Left(facebookLoginError) => facebookLoginError shouldBe FacebookTokenError(
          FacebookError("Error validating client secret."))
      }
    }

    "return facebook error with on invalid secret and clientId either" in {
      facebookWrongClientIdAndSecret.appAccessTokenEither() map {
        case Right(_) => fail("right unexpected in wrong secret")
        case Left(facebookLoginError) => facebookLoginError shouldBe FacebookTokenError(
          FacebookError("Invalid Client ID"))
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
