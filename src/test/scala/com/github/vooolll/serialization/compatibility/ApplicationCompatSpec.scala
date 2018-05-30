package com.github.vooolll.serialization.compatibility

import java.net.URL

import com.github.vooolll.domain.oauth.FacebookAppId
import com.github.vooolll.domain.profile.FacebookApplication
import com.github.vooolll.serialization.FacebookDecoders._

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
