package com.github.vooolll.services

import cats.implicits._
import com.github.vooolll.base._
import com.github.vooolll.config.FacebookConfig._
import com.github.vooolll.domain.comments.FacebookCommentsAttributes
import com.github.vooolll.domain.oauth.FacebookToken
import com.github.vooolll.domain.permission.FacebookPermissions.UserDataPermissions.Posts
import com.github.vooolll.domain.posts.{FacebookPostAttributes, FacebookPostId}
import com.github.vooolll.domain.profile.{FacebookProfileId, FacebookUserId}
import com.github.vooolll.syntax.FacebookShowOps._
import org.scalatest.{Matchers, WordSpec}

class FacebookUrlsSpec extends WordSpec with Matchers {

  val v = version.value

  "FacebookUrls" should {
    "return url to obtain log lived uri" in {
      TestUrls
        .longLivedTokenUri("test")
        .toString() shouldBe s"https://graph.facebook.com/v$v/oauth/access_token" +
        s"?client_id=${clientId.show}" +
        s"&client_secret=${appSecret.show}" +
        s"&grant_type=fb_exchange_token" +
        s"&fb_exchange_token=test"
    }

    "return post uri" in {
      TestUrls
        .postUri(FacebookPostId("postId"), userAccessToken, Set.empty)
        .toString() shouldBe "https://graph.facebook.com" +
        s"/v$v/postId?access_token=token"
    }

    "return feed uri" in {
      TestUrls
        .userFeedUri(
          userAccessToken,
          FacebookUserId("me"),
          FacebookPostAttributes.defaultPostAttributeValues
        )
        .toString() shouldBe "https://graph.facebook.com" +
        s"/v$v/me/feed?access_token=token&fields=${FacebookPostAttributes.defaultPostAttributeValues.map(_.value).mkString("%2C")}"
    }

    "return auth uri" in {
      TestUrls
        .buildAuthUrl(Set.empty)
        .toString() shouldBe s"https://facebook.com/v$v/dialog/oauth" +
        s"?client_id=${clientId.show}" +
        s"&redirect_uri=http%3A%2F%2Flocalhost%3A9000%2Fredirect" +
        s"&response_type=code"
    }

    "return auth uri with state, response_type and permissions" in {
      TestUrls
        .buildAuthUrl(
          permissions  = Set(Posts),
          responseType = FacebookToken,
          state        = "asd".some
        )
        .toString() shouldBe s"https://facebook.com/v$v/dialog/oauth" +
        s"?client_id=${clientId.show}" +
        s"&redirect_uri=http%3A%2F%2Flocalhost%3A9000%2Fredirect" +
        s"&response_type=${FacebookToken.value}" +
        s"&scope=${Posts.value}" +
        s"&state=asd"
    }

    "return likes uri" in {
      TestUrls
        .likesUri(FacebookPostId("postId"), userAccessToken)
        .toString() shouldBe "https://graph.facebook.com" +
        s"/v$v/postId/likes?access_token=token&summary=false"
    }

    "return likes uri with summary" in {
      TestUrls
        .likesUri(FacebookPostId("postId"), userAccessToken, summary = true)
        .toString() shouldBe "https://graph." +
        s"facebook.com/v$v/postId/likes?access_token=token&summary=true"
    }

    "return comment uri" in {
      TestUrls
        .commentsUri(
          FacebookPostId("postId"),
          userAccessToken,
          FacebookCommentsAttributes.defaultCommentsAttributeValues
        )
        .toString() shouldBe "https://graph.facebook.com" +
        s"/v$v/postId/comments?access_token=token&summary=false" +
        s"&fields=${FacebookCommentsAttributes.defaultCommentsAttributeValues.map(_.value).mkString("%2C")}"
    }

    "return comment uri with summary" in {
      TestUrls
        .commentsUri(
          FacebookPostId("postId"),
          userAccessToken,
          FacebookCommentsAttributes.defaultCommentsAttributeValues,
          summary = true
        )
        .toString() shouldBe "https://graph." +
        s"facebook.com/v$v/postId/comments?access_token=token&summary=true" +
        s"&fields=${FacebookCommentsAttributes.defaultCommentsAttributeValues.map(_.value).mkString("%2C")}"
    }

    "return albums uri" in {
      TestUrls
        .albumsUri(FacebookProfileId("profileId"), userAccessToken)
        .toString() shouldBe "https://graph." +
        s"facebook.com/v$v/profileId/albums?access_token=token"
    }

    "return taggable_friends uri" in {
      TestUrls
        .friendsUri(userAccessToken, FacebookUserId("123"), Set.empty)
        .toString() shouldBe "https://graph." +
        s"facebook.com/v$v/123/friends?access_token=token"
    }

  }

}
