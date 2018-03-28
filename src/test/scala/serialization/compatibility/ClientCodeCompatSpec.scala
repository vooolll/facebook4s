package serialization.compatibility

import domain.oauth.FacebookClientCode
import serialization.FacebookDecoders._

class ClientCodeCompatSpec extends CompatibilitySpec {

  val clientCodePath = "testdata/client_code.json"

  "FacebookClientCode" should {
    s"be compatible with $clientCodePath" in {
      decodeJson[FacebookClientCode](clientCodePath) shouldBe clientCode
    }
  }

}
