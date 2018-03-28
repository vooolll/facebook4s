package serialization.compatibility

import domain.profile.FacebookUser
import serialization.FacebookDecoders._

class UserCompatSpec extends CompatibilitySpec {

  val userPath = "testdata/basic_user.json"

  "FacebookUser" should {
    s"be compatible with $userPath" in {
      decodeJson[FacebookUser](userPath) shouldBe user
    }
  }

}
