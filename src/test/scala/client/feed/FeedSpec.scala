package client.feed

import base.FacebookClientSupport
import cats.implicits._
import base.TestConfiguration._

class FeedSpec extends FacebookClientSupport {

  "Facebook Graph Api" should {
    "return feed" in { c =>
      c.feed(userId, userTokenRaw) map(_.pictureWithoutQueryParams shouldBe feed)
    }

    "return feed result" in { c =>
      c.feedResult(userId, userTokenRaw) map(f => f.map(_.pictureWithoutQueryParams) shouldBe feed.asRight)
    }
  }
}
