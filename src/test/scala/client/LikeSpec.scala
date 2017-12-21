package client

import domain.likes.{FacebookLike, FacebookLikes, FacebookLikesPaging}
import cats.implicits._
import domain.posts.FacebookPostId
import domain.profile.FacebookUserId
import config.TestConfiguration._

class LikeSpec extends FacebookClientSupport {

  val like = FacebookLike(FacebookUserId("117656352360395"), "Bob Willins".some)
  val likePaging = FacebookLikesPaging("MTE3NjU2MzUyMzYwMzk1".some, "MTE3NjU2MzUyMzYwMzk1".some)
  val likes = FacebookLikes(List(like), likePaging)

  "Facebook Graph Api" should {
    "return likes of post" in { c =>
      c.likes(FacebookPostId("117656352360395_117607245698639"), userTokenRaw) map (_ shouldBe likes)
    }
  }
}
