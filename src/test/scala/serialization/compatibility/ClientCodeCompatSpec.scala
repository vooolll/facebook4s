package serialization.compatibility

import base.{JsonSerializationSupport, SyncSpec}
import domain.oauth.FacebookClientCode
import serialization.FacebookDecoders._

class ClientCodeCompatSpec extends SyncSpec with JsonSerializationSupport {

  val clientCodePath = "testdata/client_code.json"

  "FacebookClientCode" should {
    s"be compatible with $clientCodePath" in {
      decodeJson[FacebookClientCode](clientCodePath).map(_ shouldBe clientCode)
    }
  }

}
