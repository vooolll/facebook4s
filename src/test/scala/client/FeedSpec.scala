package client

import java.text.SimpleDateFormat

import domain.FacebookUserId
import domain.feed.{FacebookPaging, FacebookPost, FacebookSimplePost, FacebookUserFeed}
import domain.oauth.{FacebookAccessToken, TokenValue, UserAccessToken}
import cats.syntax.option._

import scala.concurrent.duration.DurationInt

class FeedSpec extends FacebookClientSpec {

  val feed = FacebookUserFeed(
    List(
      FacebookSimplePost("499313270413277_504668796544391", "Valeryi Baibossynov updated his profile picture.",
        toInstant("2017-10-01T13:43:05+0000")),
      FacebookSimplePost("499313270413277_139299253081349",
        "Valeryi Baibossynov added a life event from May 2, 1993: Born on May 2, 1993.",
        toInstant("1993-05-02T07:00:00+0000"))
    ),
    FacebookPaging("https://graph.facebook.com1".some, "https://graph.facebook.com".some))


  def toInstant(string: String) = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(string).toInstant

  "Facebook Graph Api" should {
    "return feed" in { c =>
      c.mockSendWithResource(resourcePath = "testdata/me_feed.json")
      c.feed(FacebookUserId("me"),
        FacebookAccessToken(TokenValue(""), UserAccessToken("", 1000.seconds))) map(_ shouldBe feed)
    }
  }
}
