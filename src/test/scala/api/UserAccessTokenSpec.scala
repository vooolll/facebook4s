package api

import org.scalatest.{AsyncWordSpec, Matchers}
import org.scalatest.mockito.MockitoSugar

class UserAccessTokenSpec extends AsyncWordSpec with Matchers with MockitoSugar with FacebookClientFixture {

  "Facebook Graph Api" should {
    "return user access token" ignore {
      facebookClient.userAccessToken("code") map {
        case Right(token) =>
          println(token)
          token.valueToken.value.isEmpty shouldBe false
        case Left(e) => fail(e.error.message)
      }
    }
  }

}
