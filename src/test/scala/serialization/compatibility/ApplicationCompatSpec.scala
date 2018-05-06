package serialization.compatibility

import java.net.URL

import domain.oauth.FacebookAppId
import domain.profile.FacebookApplication
import serialization.FacebookDecoders._

class ApplicationCompatSpec extends CompatibilitySpec {

  val applicationPath = "testdata/basic_application.json"

  val application = FacebookApplication(FacebookAppId("1969406143275709"),
    new URL("https://www.facebook.com/games/?app_id=1969406143275709"),
    "testing_app")

  "FacebookApplication" should {
    s"be compatible with $applicationPath" in {
      decodeJson[FacebookApplication](applicationPath) shouldBe application
    }
  }
}
