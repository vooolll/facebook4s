package serialization.compatibility

import base.{JsonSerializationSupport, SyncSpec}

class TaggableFriendCompatSpec extends SyncSpec with JsonSerializationSupport {

  val taggableFriendPath = "testdata/taggable_friend.json"

  "TaggableFriend" should {
    "be compatible with" in {
      fail()
    }
  }
}
