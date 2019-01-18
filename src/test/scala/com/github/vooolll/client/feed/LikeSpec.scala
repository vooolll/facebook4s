package com.github.vooolll.client.feed

import com.github.vooolll.base.FacebookClientSupport
import cats.implicits._
import com.github.vooolll.domain.FacebookPaging
import com.github.vooolll.domain.likes.{FacebookLike, FacebookLikes, FacebookLikesSummary}
import com.github.vooolll.domain.profile.FacebookUserId

class LikeSpec extends FacebookClientSupport {

  import com.github.vooolll.base.TestConfiguration._

  val like = FacebookLike(FacebookUserId("117656352360395"), "Bob Willins".some)
  val likePaging = FacebookPaging("MTE3NjU2MzUyMzYwMzk1".some, "MTE3NjU2MzUyMzYwMzk1".some)
  val likes = FacebookLikes(List(like), paging = None)
  val likesSummary = FacebookLikesSummary(totalCount = 1, canLike = true.some, hasLikes = true.some)

  val likesWithSummary = likes.copy(summary = likesSummary.some)

  "Facebook Graph Api" should {
    "return likes of post" in { c =>
      c.likes(postId).withoutPaging map (_ shouldBe likes)
    }

    "return likes of post result" in { c =>
      c.likesResult(postId).withoutPaging map (_ shouldBe likes.asRight)
    }

    "return likes of post with summary" in { c =>
      c.likes(postId, summary = true).withoutPaging map (_ shouldBe likesWithSummary)
    }

    "return likes of post result with summary" in { c =>
      c.likesResult(postId, summary = true).withoutPaging map (_ shouldBe likesWithSummary.asRight)
    }
  }
}
