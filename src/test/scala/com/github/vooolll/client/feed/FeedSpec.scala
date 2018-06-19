package com.github.vooolll.client.feed

import com.github.vooolll.base.FacebookClientSupport
import cats.implicits._

class FeedSpec extends FacebookClientSupport {

  import com.github.vooolll.base.TestConfiguration._

  "Facebook Graph Api" should {
    "return feed" in { c =>
      c.feed(userId) map(_.pictureWithoutQueryParams shouldBe feed)
    }

    "return feed result" in { c =>
      c.feedResult(userId) map(_.map(_.pictureWithoutQueryParams) shouldBe feed.asRight)
    }
  }
}
