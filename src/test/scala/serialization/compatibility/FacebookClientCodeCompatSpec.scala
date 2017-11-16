package serialization.compatibility

import base.SyncSpec
import domain.oauth.FacebookClientCode
import serialization.FacebookDecoders._

class FacebookClientCodeCompatSpec extends SyncSpec with JsonSerializationSupport {

  val clientCodePath = "testdata/client_code.json"

  "FacebookClientCode" should {
    s"be compatible with $clientCodePath" in {
      decodeJson[FacebookClientCode](clientCodePath) shouldBe TestEntities.clientCode
    }
  }

}
