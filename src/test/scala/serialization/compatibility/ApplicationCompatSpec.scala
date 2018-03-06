package serialization.compatibility

import base.{JsonSerializationSupport, SyncSpec}
import domain.profile.FacebookApplication
import serialization.FacebookDecoders._

class ApplicationCompatSpec extends SyncSpec with JsonSerializationSupport {

  val applicationPath = "testdata/basic_application.json"

  "FacebookApplication" should {
    s"be compatible with $applicationPath" in {
      decodeJson[FacebookApplication](applicationPath) shouldBe application
    }
  }
}
