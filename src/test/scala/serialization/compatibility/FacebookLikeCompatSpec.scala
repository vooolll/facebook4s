package serialization.compatibility

import base.SyncSpec
import domain.likes.FacebookLike
import serialization.FacebookDecoders._
import TestEntities._

class FacebookLikeCompatSpec extends SyncSpec with JsonSerializationSupport {

  val likePath = "testdata/like.json"

  "FacebookFeed" should {
    s"be compatible with $likePath" in {
      decodeJson[FacebookLike](likePath) shouldBe like
    }
  }
}
