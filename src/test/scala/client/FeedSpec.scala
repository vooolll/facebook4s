package client

import domain.posts.{FacebookPost, FacebookPostId}
import domain.profile.{FacebookProfileId, FacebookUserId}
import serialization.compatibility.toInstant
import config.TestConfiguration._
import domain.feed.{FacebookFeed, FacebookPaging}
import cats.implicits._

class FeedSpec extends FacebookClientSupport {

  val realPostId = FacebookPostId("117656352360395_120118735447490")

  val userId = FacebookUserId("117656352360395")

  val realPost = FacebookPost(
    realPostId,
    Some("Bob Willins updated her cover photo."),
    Some(toInstant("2017-12-19T14:08:44+0000")),
    Some("120118675447496"),
    Some("https://scontent.xx.fbcdn.net/v/t1.0-0/s130x130/25398995_120118675447496_5830741756468130361_n.jpg?" +
      "oh=63adaf511c1129c82283d1ddc4244448&oe=5ACE1813"),
    Some(FacebookProfileId("117656352360395")))

  val realPost1 = FacebookPost(
    FacebookPostId("117656352360395_117607245698639"),
    Some("Bob Willins updated her profile picture."),
    Some(toInstant("2017-12-18T11:30:10+0000")),
    Some("117607225698641"),
    Some("https://scontent.xx.fbcdn.net/v/t1.0-0/s130x130/25396081_117607225698641_6348338142026249400_n.jpg?" +
      "oh=d92eb6df1c795c436a600cd8ac8759ec&oe=5ACD0782"),
    Some(FacebookProfileId("117656352360395")))

  val paging = FacebookPaging(
    Some("https://graph.facebook.com/v2.11/117656352360395/feed?fields=id,story,created_time,object_id,picture,from" +
      "&access_token=EAAcAL79ZCFjMBAGMIwVasYODBBZAXi2QQmMR3R6DTBYYfAcpkftWBXGlQiFHmvZAPFd5M444mnAiwqzvS1ooziExJp9rq" +
      "zI3FFhtdzbopZCVDKObgh6ewfmDErB7PtSyGbSI1q6SEp2eQcMwq7B4rh7ZAtzxHZAhCDCDbjPV6psCwZA1I4Wcb5x&limit=25&until=15" +
      "13596610&__paging_token=enc_AdBcIKGgOAbPox4xsIiSOefrZB0Q3aubqn57sZCqgjhMbzdBD6Kt5jeKpngUrNXGNIQHVtPr0dfy5n4S" +
      "auEoFsr2ApzN40Jxb2VXbZBiNGT8TjJJwZDZD"),
    Some("https://graph.facebook.com/v2.11/117656352360395/feed?fields=id,story,created_time,object_id,picture,from" +
      "&since=1513692524&access_token=EAAcAL79ZCFjMBAGMIwVasYODBBZAXi2QQmMR3R6DTBYYfAcpkftWBXGlQiFHmvZAPFd5M444mnAi" +
      "wqzvS1ooziExJp9rqzI3FFhtdzbopZCVDKObgh6ewfmDErB7PtSyGbSI1q6SEp2eQcMwq7B4rh7ZAtzxHZAhCDCDbjPV6psCwZA1I4Wcb5x&" +
      "limit=25&__paging_token=enc_AdCES1F18OvZCu2nFAIVLb6KtNttBdZBFYY0pu41aoKx8Ef8M76cxG9p7HsOfs4H17MJ2JXwvy3bkAk1" +
      "2gUmcAp7vAP2ojten38tv7oUYlk7ModAZDZD&__previous=1"))

  val feed = FacebookFeed(List(realPost, realPost1), paging)

  "Facebook Graph Api" should {
    "return feed" in { c =>
      c.feed(userId, userTokenRaw) map(_ shouldBe feed)
    }

    "return feed result" in { c =>
      c.feedResult(userId, userTokenRaw) map(_ shouldBe feed.asRight)
    }
  }
}
