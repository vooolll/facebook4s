package serialization.compatibility

import domain.profile.FacebookApplication
import serialization.FacebookDecoders._

class ApplicationCompatSpec extends CompatibilitySpec {

  val applicationPath = "testdata/basic_application.json"

  "FacebookApplication" should {
    s"be compatible with $applicationPath" in {
      decodeJson[FacebookApplication](applicationPath) shouldBe application
    }
  }
}
