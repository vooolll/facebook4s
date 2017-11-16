package client

import domain.FacebookUserId
import domain.oauth.{FacebookAccessToken, TokenValue, UserAccessToken}
import serialization.compatibility.TestEntities

import scala.concurrent.duration.DurationInt

class FeedSpec extends FacebookClientSupport {

  "Facebook Graph Api" should {
    "return feed" in { c =>
      c.mockSendWithResource(resourcePath = "testdata/me_feed.json")
      c.feed(FacebookUserId("me"),
        FacebookAccessToken(TokenValue(""), UserAccessToken("", 1000.seconds))) map(_ shouldBe TestEntities.feed)
    }
  }
}
