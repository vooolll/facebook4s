package client.feed

import base.FacebookClientSupport
import cats.implicits._
import base.TestConfiguration._

class PostSpec extends FacebookClientSupport {

  "Facebook Graph Api" should {
    "return posts" in { c =>
      c.post(postId, userAccessToken) map(_.withoutQueryParams shouldBe post)
    }

    "return posts result" in { c =>
      c.postResult(postId, userAccessToken) map(p => p.map(_.withoutQueryParams) shouldBe post.asRight)
    }
  }

}
