package client

import java.time.ZoneOffset

import client._
import config.TestConfiguration._
import domain.profile._
import cats.implicits._
import org.apache.commons.lang3.LocaleUtils
import serialization.compatibility.TestEntities.toInstant

class UserSpec extends FacebookClientSupport {
  val realUser = FacebookUser(
    FacebookUserId("499283963749541"),Some("Valeryi Baibossynov"),
    Some(FacebookUserPicture(50.0, isSilhouette = false,"https://scontent.xx.fbcdn.net/v/t1.0-1/p50x50/22728655_5" +
      "13792128965391_443796664145972604_n.jpg?oh=96ab05455244b5f7062d2a194e30aa8e&oe=5A88C8AD",50.0)),
    Some("Valeryi"), Some("Baibossynov"), Some("https://www.facebook.com/app_scoped_user_id/499283963749541/"), Some(true),
    Some(LocaleUtils.toLocale("en_US")), ZoneOffset.ofHours(2).some , Gender.Male.some, AgeRange(21,None).some,
    Some(Cover("527696177574986",0.0,0.0,"https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/23905322_527696177574986_8012" +
      "137948429389386_n.jpg?oh=884e95c27726951641b1cd7cc4bbe9eb&oe=5ACA15B0")),
    Some(toInstant("2017-11-11T00:10:08+0000")))

  "Facebook Graph Api" should {
    "return user profile" in { c =>
      c.userProfile(FacebookUserId("499283963749541"), userTokenRaw) map (_ shouldBe realUser)
    }

    "return user profile result" in { c =>
      c.userProfileResult(FacebookUserId("499283963749541"), userTokenRaw) map (
        _ shouldBe realUser.asRight)
    }
  }
}
