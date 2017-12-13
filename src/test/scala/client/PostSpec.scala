package client

import serialization.compatibility.TestEntities.{userAccessToken, postId, post}
import cats.implicits._

class PostSpec extends FacebookClientStubSupport {

  "Facebook Graph Api" should {
    "return posts" in { c =>
      c.mockSendWithResource(resourcePath = "testdata/post.json")
      c.post(postId, userAccessToken) map(_ shouldBe post)
      c.postResult(postId, userAccessToken) map(_ shouldBe post.asRight)
    }
  }

}
