package client

import serialization.compatibility._

class FeedSpec extends FacebookClientStubSupport {

  "Facebook Graph Api" should {
    "return feed" in { c =>
      c.mockSendWithResource(resourcePath = "testdata/me_feed.json")
      c.feed(userId, userAccessToken) map(_ shouldBe feed)
    }
  }
}
