package client.feed

import base.FacebookClientSupport
import cats.implicits._
import base.TestConfiguration._
import domain.likes.{FacebookLike, FacebookLikes, FacebookPaging, FacebookLikesSummary}
import domain.profile.FacebookUserId

class LikeSpec extends FacebookClientSupport {

  val like = FacebookLike(FacebookUserId("117656352360395"), "Bob Willins".some)
  val likePaging = FacebookPaging("MTE3NjU2MzUyMzYwMzk1".some, "MTE3NjU2MzUyMzYwMzk1".some)
  val likes = FacebookLikes(List(like), likePaging)
  val likesSummary = FacebookLikesSummary(totalCount = 1, canLike = true.some, hasLikes = true.some)

  val likesWithSummary = likes.copy(summary = likesSummary.some)

  "Facebook Graph Api" should {
    "return likes of post" in { c =>
      c.likes(postId, userTokenRaw) map (_ shouldBe likes)
    }

    "return likes of post result" in { c =>
      c.likesResult(postId, userTokenRaw) map (_ shouldBe likes.asRight)
    }

    "return likes of post with summary" in { c =>
      c.likes(postId, userTokenRaw, summary = true) map (_ shouldBe likesWithSummary)
    }

    "return likes of post result with summary" in { c =>
      c.likesResult(postId, userTokenRaw, summary = true) map (_ shouldBe likesWithSummary.asRight)
    }
  }
}
