package client.feed

import cats.implicits._
import client.FacebookClientSupport
import config.TestConfiguration._

class PostSpec extends FacebookClientSupport {

  "Facebook Graph Api" should {
    "return posts" in { c =>
      c.post(postId, userAccessToken) map(_ shouldBe post)
    }

    "return posts result" in { c =>
      c.postResult(postId, userAccessToken) map(_ shouldBe post.asRight)
    }
  }

}
