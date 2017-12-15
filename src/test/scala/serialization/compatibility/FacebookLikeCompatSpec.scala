package serialization.compatibility

import base.SyncSpec
import domain.likes.FacebookLike
import serialization.FacebookDecoders._

class FacebookLikeCompatSpec extends SyncSpec with JsonSerializationSupport {

  val likePath = "testdata/like.json"

  "FacebookLike" should {
    s"be compatible with $likePath" in {
      decodeJson[FacebookLike](likePath) shouldBe like
    }
  }
}
