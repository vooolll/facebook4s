package com.github.vooolll.serialization.compatibility

import com.github.vooolll.base._
import com.github.vooolll.domain.oauth.FacebookClientCode
import com.github.vooolll.serialization.FacebookDecoders._

class ClientCodeCompatSpec extends CompatibilitySpec {

  val clientCodePath = "testdata/client_code.json"

  "FacebookClientCode" should {
    s"be compatible with $clientCodePath" in {
      decodeJson[FacebookClientCode](clientCodePath) shouldBe clientCode
    }
  }

}
