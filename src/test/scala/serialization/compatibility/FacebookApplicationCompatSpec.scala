package serialization.compatibility

import base.SyncSpec
import domain.profile.FacebookApplication
import serialization.FacebookDecoders._

class FacebookApplicationCompatSpec extends SyncSpec with JsonSerializationSupport {

  val applicationPath = "testdata/basic_application.json"

  "FacebookApplication" should {
    s"be compatible with $applicationPath" in {
      decodeJson[FacebookApplication](applicationPath) map(_ shouldBe application)
    }
  }
}
