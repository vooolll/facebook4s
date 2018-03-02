package client

import java.net.URL
import java.time.ZoneOffset

import feed._
import base.FacebookClientSupport
import cats.implicits._
import base.TestConfiguration._
import domain.oauth.FacebookError
import domain.profile._
import org.apache.commons.lang3.LocaleUtils
import serialization.compatibility._

class UserSpec extends FacebookClientSupport {
  val realUserId = FacebookUserId("117656352360395")

  val realUser = FacebookUser(
    realUserId, Some("Bob Willins"),
    Some(FacebookUserPicture(50.0, isSilhouette = false, new URL("https://scontent.xx.fbcdn.net/v/t1.0-1/c25.0.50.50/p50x50/" +
      "25396081_117607225698641_6348338142026249400_n.jpg"), 50.0)),
    Some("Bob"), Some("Willins"), Some(new URL("https://www.facebook.com/app_scoped_user_id/117656352360395/")), Some(true),
    Some(LocaleUtils.toLocale("en_US")), ZoneOffset.ofHours(2).some , Gender.Female.some, AgeRange(21,None).some,
    Some(Cover("120118675447496",0.0,50.0, new URL("https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/25398995_120118675447" +
      "496_5830741756468130361_n.jpg"))),
    Some(toInstant("2017-12-18T11:30:11+0000")))

  "Facebook Graph Api" should {
    "return user profile" in { c =>
      c.userProfile(realUserId, userTokenRaw) map (_.withoutQueryParams shouldBe realUser)
    }

    "return user profile result" in { c =>
      c.userProfileResult(realUserId, userTokenRaw) map (_.map(_.withoutQueryParams) shouldBe realUser.asRight)
    }

    "return error SpecifiedObjectNotFound" in { c =>
      c.userProfileResult(realUserId.copy(value = "asd"), userTokenRaw) map {
        case Right(_) => fail("bad request expected")
        case Left(e)  => e.errorType shouldBe FacebookError.SpecifiedObjectNotFound
      }
    }

    "return error InvalidVerificationCodeFormat" in { c =>
      c.userProfileResult(realUserId.copy(value = "777661112359912"), userTokenRaw) map {
        case Right(_) => fail("bad request expected")
        case Left(e)  => e.errorType shouldBe FacebookError.InvalidVerificationCodeFormat
      }
    }
  }
}
