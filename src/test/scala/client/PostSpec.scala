package client

import cats.implicits._
import config.TestConfiguration._
import domain.posts.{FacebookPost, FacebookPostId}
import domain.profile.FacebookProfileId
import serialization.compatibility.toInstant

class PostSpec extends FacebookClientSupport {

  val realPostId = FacebookPostId("117656352360395_120118735447490")

  val realPost = FacebookPost(
    realPostId,
    Some("Bob Willins updated her cover photo."),
    Some(toInstant("2017-12-19T14:08:44+0000")),
    Some("120118675447496"),
    Some("https://scontent.xx.fbcdn.net/v/t1.0-0/s130x130/25398995_120118675447496_5830741756468130361_n.jpg?" +
      "oh=63adaf511c1129c82283d1ddc4244448&oe=5ACE1813"),
    Some(FacebookProfileId("117656352360395")))

  "Facebook Graph Api" should {
    "return posts" in { c =>
      c.post(realPostId, userAccessToken) map(_ shouldBe realPost)
    }

    "return posts result" in { c =>
      c.postResult(realPostId, userAccessToken) map(_ shouldBe realPost.asRight)
    }
  }

}
