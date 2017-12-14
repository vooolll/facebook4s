package client

import client._
import cats.implicits._
import config.TestConfiguration._
import domain.posts.{FacebookPost, FacebookPostId}
import domain.profile.FacebookProfileId
import serialization.compatibility.TestEntities.toInstant

class PostSpec extends FacebookClientSupport {

  val realPostId = FacebookPostId("499283963749541_527696260908311")

  val realPost = FacebookPost(
    FacebookPostId("499283963749541_527696260908311"),
    Some("Valeryi Baibossynov updated his cover photo."),
    Some(toInstant("2017-11-27T14:54:19+0000")),
    Some("527696177574986"),
    Some("https://scontent.xx.fbcdn.net/v/t1.0-0/q86/s130x130/23905322_527696177574986_8012137948429389386_n.jpg" +
      "?oh=077a8c93633c68f1666047708f1fe749&oe=5ACCB40D"),
    Some(FacebookProfileId("499283963749541")))

  "Facebook Graph Api" should {
    "return posts" in { c =>
      c.post(realPostId, userAccessToken) map(_ shouldBe realPost)
    }

    "return posts result" in { c =>
      c.postResult(realPostId, userAccessToken) map(_ shouldBe realPost.asRight)
    }
  }

}
