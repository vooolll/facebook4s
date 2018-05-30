package com.github.vooolll.serialization.compatibility

import cats.implicits._
import com.github.vooolll.domain.FacebookPaging
import com.github.vooolll.domain.likes._
import com.github.vooolll.domain.profile.FacebookUserId
import com.github.vooolll.serialization.FacebookDecoders._

class LikesCompatSpec extends CompatibilitySpec {

  val likesPath = "testdata/likes.json"
  val likePath = "testdata/like.json"
  val likesPagingPath = "testdata/likes_paging.json"
  val likesSummaryPath = "testdata/likes_summary.json"
  val likesWithSummaryPath = "testdata/likes_with_summary.json"

  val likesSummary = FacebookLikesSummary(totalCount = 1, canLike = true.some, hasLikes = true.some)

  val like = FacebookLike(FacebookUserId("215080582368050"), "Iana Baibossynova".some)

  val likesPaging = FacebookPaging("MTkzMDAwNzk1MDU5NTAzOAZDZD".some, "MjE1MDgwNTgyMzY4MDUw".some)

  val likes = FacebookLikes(List(like), Some(likesPaging))

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
