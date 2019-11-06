package com.github.vooolll.serialization.compatibility

import com.github.vooolll.base._
import com.github.vooolll.domain.oauth._
import com.github.vooolll.serialization.FacebookDecoders._

class AccessTokenCompatSpec extends CompatibilitySpec {

  val userAccessTokenPath = "testdata/user_access_token.json"
  val appAccessTokenPath  = "testdata/app_access_token.json"

  val appAccessToken = FacebookAccessToken(
    TokenValue("1234567891011121|A6BCDEFiGASDFdB1_Zviht7lzxc"),
    AppAccessToken("bearer")
  )

  "FacebookAccessToken" should {
    s"be compatible with $userAccessTokenPath" in {
      val decoded = decodeJson[FacebookAccessToken](userAccessTokenPath)(
        decodeUserAccessToken
      )
      decoded shouldBe userAccessToken
    }

    s"be compatible with $appAccessTokenPath" in {
      val decoded = decodeJson[FacebookAccessToken](appAccessTokenPath)(
        decodeAppAccessToken
      )
      decoded shouldBe appAccessToken
    }
  }

}
