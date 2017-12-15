package serialization.compatibility

import base.SyncSpec
import domain.oauth.FacebookOauthError
import serialization.FacebookDecoders._

class FacebookOauthErrorCompatSpec extends SyncSpec with JsonSerializationSupport {

  val wrongCodePath = "testdata/oauth_error.json"

  "FacebookOauthError" should {
    s"be compatible with $wrongCodePath" in {
      decodeJson[FacebookOauthError](wrongCodePath) shouldBe facebookOauthError
    }
  }

}
