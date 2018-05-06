package serialization.compatibility

import domain.oauth.FacebookError
import serialization.FacebookDecoders._

class FacebookErrorCompatSpec extends CompatibilitySpec {

  val wrongCodePath = "testdata/oauth_error.json"

  val facebookError = FacebookError("Invalid verification code format.",
    FacebookError.InvalidVerificationCodeFormat)

  "FacebookError" should {
    s"be compatible with $wrongCodePath" in {
      decodeJson[FacebookError](wrongCodePath) shouldBe facebookError
    }
  }

}
