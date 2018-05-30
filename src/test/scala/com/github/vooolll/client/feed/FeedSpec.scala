package com.github.vooolll.client.feed

import com.github.vooolll.base.FacebookClientSupport
import cats.implicits._
import com.github.vooolll.base.TestConfiguration._

class FeedSpec extends FacebookClientSupport {

  "Facebook Graph Api" should {
    "return feed" in { c =>
      c.feed(userId, userTokenRaw) map(_.pictureWithoutQueryParams shouldBe feed)
    }

    "return feed result" in { c =>
      c.feedResult(userId, userTokenRaw) map(_.map(_.pictureWithoutQueryParams) shouldBe feed.asRight)
    }
  }
}
