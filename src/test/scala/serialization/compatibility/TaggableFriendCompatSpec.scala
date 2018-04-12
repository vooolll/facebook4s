package serialization.compatibility

import domain.friends.FacebookTaggableFriend
import serialization.FacebookDecoders.decodeTaggableFriend

class TaggableFriendCompatSpec extends CompatibilitySpec {

  val taggableFriendPath = "testdata/taggable_friend.json"

  "TaggableFriend" should {
    "be compatible with" in {
      decodeJson[FacebookTaggableFriend](taggableFriendPath) shouldBe taggableFriend
    }
  }
}
