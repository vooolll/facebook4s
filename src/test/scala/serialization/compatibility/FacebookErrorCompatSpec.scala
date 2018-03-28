package serialization.compatibility

import domain.oauth.FacebookError
import serialization.FacebookDecoders._

class FacebookErrorCompatSpec extends CompatibilitySpec {

  val wrongCodePath = "testdata/oauth_error.json"

  "FacebookError" should {
    s"be compatible with $wrongCodePath" in {
      decodeJson[FacebookError](wrongCodePath) shouldBe facebookError
    }
  }

}
