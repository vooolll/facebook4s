package com.github.vooolll.serialization.compatibility

import java.net.URL

import com.github.vooolll.base._
import com.github.vooolll.domain.feed._
import com.github.vooolll.domain.posts.{FacebookPost, FacebookPostId}
import com.github.vooolll.serialization.FacebookDecoders._
import cats.implicits._

class FeedCompatSpec extends CompatibilitySpec {

  val feedPath = "testdata/me_feed.json"

  val feed = FacebookFeed(
    List(
      FacebookPost(FacebookPostId("499313270413277_504668796544391"), "Valeryi Baibossynov updated his profile picture.".some,
        Some(toInstant("2017-10-01T13:43:05+0000")), None, None, None),

      FacebookPost(FacebookPostId("499313270413277_139299253081349"),
        "Valeryi Baibossynov added a life event from May 2, 1993: Born on May 2, 1993.".some,
        Some(toInstant("1993-05-02T07:00:00+0000")), None, None, None)
    ),
    FacebookFeedPaging(new URL("https://graph.facebook.com1").some, new URL("https://graph.facebook.com").some))


  "FacebookFeed" should {
    s"be compatible with $feedPath" in {
      decodeJson[FacebookFeed](feedPath) shouldBe feed
    }
  }
}
