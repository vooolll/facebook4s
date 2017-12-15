package serialization.compatibility

import base.SyncSpec
import domain.likes.FacebookLikes
import serialization.FacebookDecoders._
import TestEntities._

class FacebookLikesCompatSpec extends SyncSpec with JsonSerializationSupport {

  val likesPath = "testdata/likes.json"

  "FacebookLikes" should {
    s"be compatible with $likesPath" in {
      decodeJson[FacebookLikes](likesPath) shouldBe likes
    }
  }

}
