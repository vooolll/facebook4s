package serialization.compatibility

import base.{JsonSerializationSupport, SyncSpec}
import domain.likes._
import serialization.FacebookDecoders._
import cats.implicits._

class LikesCompatSpec extends SyncSpec with JsonSerializationSupport {

  val likesPath = "testdata/likes.json"
  val likePath = "testdata/like.json"
  val likesPagingPath = "testdata/likes_paging.json"
  val likesSummaryPath = "testdata/likes_summary.json"
  val likesWithSummaryPath = "testdata/likes_with_summary.json"

  "FacebookLikes" should {
    s"be compatible with $likesPath" in {
      decodeJson[FacebookLikes](likesPath) shouldBe likes
    }

    s"be compatible with $likePath" in {
      decodeJson[FacebookLike](likePath) shouldBe like
    }

    s"be compatible with $likesPagingPath" in {
      decodeJson[FacebookPaging](likesPagingPath) shouldBe likesPaging
    }

    s"be compatible with $likesSummaryPath" in {
      decodeJson[FacebookLikesSummary](likesSummaryPath) shouldBe likesSummary
    }

    s"be compatible with $likesWithSummaryPath" in {
      decodeJson[FacebookLikes](likesWithSummaryPath) shouldBe likes.copy(
        summary = likesSummary.some)
    }
  }

}
