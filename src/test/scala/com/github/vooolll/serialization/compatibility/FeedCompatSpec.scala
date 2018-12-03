package com.github.vooolll.serialization.compatibility

import java.net.URL

import com.github.vooolll.base._
import com.github.vooolll.domain.feed._
import com.github.vooolll.domain.media._
import com.github.vooolll.domain.posts.{FacebookPost, FacebookPostId}
import com.github.vooolll.serialization.FacebookDecoders._
import cats.implicits._

class FeedCompatSpec extends CompatibilitySpec {

  val feedPath = "testdata/me_feed.json"

  val feed = FacebookFeed(
    List(
      FacebookPost(
        id = FacebookPostId("499313270413277_504668796544391"),
        message = "Valeryi Baibossynov updated his profile picture.".some,
        name = "Valeryi Baibossynov updated his profile picture.".some,
        createdTime = Some(toInstant("2017-10-01T13:43:05+0000")),
        objectId = None,
        picture = None,
        from = None,
        attachments = Nil),

      FacebookPost(
        id = FacebookPostId("499313270413277_139299253081349"),
        message = "Valeryi Baibossynov added a life event from May 2, 1993: Born on May 2, 1993.".some,
        name = "Valeryi Baibossynov added a life event from May 2, 1993: Born on May 2, 1993.".some,
        createdTime = Some(toInstant("1993-05-02T07:00:00+0000")),
        objectId = None,
        picture = None,
        from = None,
        attachments = List(
          FacebookAttachment(
            attachment = Some(FacebookImageSource(height = 360, src = new URL(
              "https://scontent.xx.fbcdn.net/v/t1.0-9/q82/s720x720/25396081_117607225698641_6348338142026249400_n.jpg"
            ), width = 720)),
            target = FacebookAttachmentTarget(
              id = None,
              url = Some(new URL("https://www.facebook.com/bob.willins.98"))
            ),
            title = Some("Bob Willins"),
            attachmentType = AttachmentTypes.ProfileMedia,
            url = Some(new URL("https://www.facebook.com/bob.willins.98"))
          )
        ))
    ),
    FacebookFeedPaging(new URL("https://graph.facebook.com1").some, new URL("https://graph.facebook.com").some))


  "FacebookFeed" should {
    s"be compatible with $feedPath" in {
      decodeJson[FacebookFeed](feedPath) shouldBe feed
    }
  }
}
