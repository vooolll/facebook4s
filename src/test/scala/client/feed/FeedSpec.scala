package client.feed

import cats.implicits._
import client.FacebookClientSupport
import config.TestConfiguration._
import domain.feed.{FacebookFeed, FacebookPaging}
import domain.posts.{FacebookPost, FacebookPostId}
import domain.profile.{FacebookProfileId, FacebookUserId}
import serialization.compatibility.toInstant

class FeedSpec extends FacebookClientSupport {

  "Facebook Graph Api" should {
    "return feed" in { c =>
      c.feed(userId, userTokenRaw) map(_ shouldBe feed)
    }

    "return feed result" in { c =>
      c.feedResult(userId, userTokenRaw) map(_ shouldBe feed.asRight)
    }
  }
}
