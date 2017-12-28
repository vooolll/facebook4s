package client

import domain.feed.{FacebookFeed, FacebookPaging}
import domain.posts.{FacebookPost, FacebookPostId}
import domain.profile.{FacebookProfileId, FacebookUserId}
import serialization.compatibility.toInstant

package object feed {
  val postId = FacebookPostId("117656352360395_120118735447490")

  val userId = FacebookUserId("117656352360395")

  val post = FacebookPost(
    postId,
    Some("Bob Willins updated her cover photo."),
    Some(toInstant("2017-12-19T14:08:44+0000")),
    Some("120118675447496"),
    Some("https://scontent.xx.fbcdn.net/v/t1.0-0/s130x130/25398995_120118675447496_5830741756468130361_n.jpg"),
    Some(FacebookProfileId("117656352360395")))

  val realPost1 = FacebookPost(
    FacebookPostId("117656352360395_117607245698639"),
    Some("Bob Willins updated her profile picture."),
    Some(toInstant("2017-12-18T11:30:10+0000")),
    Some("117607225698641"),
    Some("https://scontent.xx.fbcdn.net/v/t1.0-0/s130x130/25396081_117607225698641_6348338142026249400_n.jpg"),
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

  val feed = FacebookFeed(List(post, realPost1), paging)


  implicit class FacebookPostWithoutLinks(post: FacebookPost) {
    def withoutQueryParams = post.copy(picture = post.picture.map(_.takeWhile(_ != '?')))
  }

  implicit class FacebookFeedWithoutLinks(feed: FacebookFeed) {
    def postsWithoutQueryParams = feed.copy(posts = feed.posts.map(_.withoutQueryParams))
  }

}
