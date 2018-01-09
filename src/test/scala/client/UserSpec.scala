package client

import java.time.ZoneOffset

import feed._
import base.FacebookClientSupport
import cats.implicits._
import base.TestConfiguration._
import domain.profile._
import org.apache.commons.lang3.LocaleUtils
import serialization.compatibility._

class UserSpec extends FacebookClientSupport {
  val realUserId = FacebookUserId("117656352360395")

  val realUser = FacebookUser(
    realUserId, Some("Bob Willins"),
    Some(FacebookUserPicture(50.0, isSilhouette = false, "https://scontent.xx.fbcdn.net/v/t1.0-1/c25.0.50.50/p50x50/" +
      "25396081_117607225698641_6348338142026249400_n.jpg?oh=26e3c9e0dc4eb770ae77f6d2e4106c4c&oe=5AB7C510",50.0)),
    Some("Bob"), Some("Willins"), Some("https://www.facebook.com/app_scoped_user_id/117656352360395/"), Some(true),
    Some(LocaleUtils.toLocale("en_US")), ZoneOffset.ofHours(2).some , Gender.Female.some, AgeRange(21,None).some,
    Some(Cover("120118675447496",0.0,50.0,"https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/25398995_120118675447" +
      "496_5830741756468130361_n.jpg")),
    Some(toInstant("2017-12-18T11:30:11+0000")))

  "Facebook Graph Api" should {
    "return user profile" in { c =>
      c.userProfile(realUserId, userTokenRaw) map (_.withoutQueryParams shouldBe realUser)
    }

    "return user profile result" in { c =>
      c.userProfileResult(realUserId, userTokenRaw) map (_.map(_.withoutQueryParams) shouldBe realUser.asRight)
    }
  }
}
