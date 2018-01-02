package serialization.compatibility

import base.{JsonSerializationSupport, SyncSpec}
import domain.oauth.FacebookOauthError
import serialization.FacebookDecoders._

class OauthErrorCompatSpec extends SyncSpec with JsonSerializationSupport {

  val wrongCodePath = "testdata/oauth_error.json"

  "FacebookOauthError" should {
    s"be compatible with $wrongCodePath" in {
      decodeJson[FacebookOauthError](wrongCodePath) map(_ shouldBe facebookOauthError)
    }
  }

}
