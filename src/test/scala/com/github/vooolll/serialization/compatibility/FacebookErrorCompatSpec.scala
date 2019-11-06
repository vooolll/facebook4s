package com.github.vooolll.serialization.compatibility

import com.github.vooolll.domain.oauth.FacebookError
import com.github.vooolll.serialization.FacebookDecoders._

class FacebookErrorCompatSpec extends CompatibilitySpec {

  val wrongCodePath = "testdata/oauth_error.json"

  val facebookError = FacebookError(
    "Invalid verification code format.",
    FacebookError.InvalidVerificationCodeFormat
  )

  "FacebookError" should {
    s"be compatible with $wrongCodePath" in {
      decodeJson[FacebookError](wrongCodePath) shouldBe facebookError
    }
  }

}
