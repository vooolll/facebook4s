package serialization.compatibility

import base.SyncSpec
import domain.profile.FacebookUser
import serialization.FacebookDecoders._

class FacebookUserCompatSpec extends SyncSpec with JsonSerializationSupport {

  val userPath = "testdata/basic_user.json"

  "FacebookUser" should {
    s"be compatible with $userPath" in {
      decodeJson[FacebookUser](userPath) shouldBe TestEntities.user
    }
  }

}
