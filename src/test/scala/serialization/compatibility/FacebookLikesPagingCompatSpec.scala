package serialization.compatibility

import base.SyncSpec
import domain.likes.FacebookLikesPaging
import serialization.FacebookDecoders._

class FacebookLikesPagingCompatSpec extends SyncSpec with JsonSerializationSupport {

  val likesPagingPath = "testdata/likes_paging.json"

  "FacebookLikesPaging" should {
    s"be compatible with $likesPagingPath" in {
      decodeJson[FacebookLikesPaging](likesPagingPath) shouldBe likesPaging
    }
  }
}
