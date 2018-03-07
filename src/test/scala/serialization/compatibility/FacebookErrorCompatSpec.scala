package serialization.compatibility

import base.{JsonSerializationSupport, SyncSpec}
import domain.oauth.FacebookError
import serialization.FacebookDecoders._

class FacebookErrorCompatSpec extends SyncSpec with JsonSerializationSupport {

  val wrongCodePath = "testdata/oauth_error.json"

  "FacebookError" should {
    s"be compatible with $wrongCodePath" in {
      decodeJson[FacebookError](wrongCodePath) shouldBe facebookError
    }
  }

}
