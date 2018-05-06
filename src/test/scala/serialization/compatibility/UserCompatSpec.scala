package serialization.compatibility

import java.net.URL
import java.time.ZoneOffset

import base._
import domain.profile._
import org.apache.commons.lang3.LocaleUtils
import serialization.FacebookDecoders._
import cats.implicits._

class UserCompatSpec extends CompatibilitySpec {

  val userPath = "testdata/basic_user.json"

  val user = FacebookUser(
    id          = FacebookUserId("499313270413277"),
    email       = "baibossynov.valery@gmail.com".some,
    name        = "Valeryi Baibossynov".some,
    lastName    = "Baibossynov".some,
    firstName   = "Valeryi".some,
    verified    = true.some,
    link        = new URL("https://www.facebook.com/app_scoped_user_id/499313270413277/").some,
    picture     = FacebookUserPicture(
      height       = 50,
      isSilhouette = false,
      url          = new URL("http://example.com"),
      wight        = 50).some,
    locale      = LocaleUtils.toLocale("en_US").some,
    timezone    = ZoneOffset.ofHours(2).some,
    gender      = Gender.Male.some,
    ageRange    = AgeRange(21, None).some,
    cover       = Cover("527696177574986".some, 0, 0, new URL("http://example.com")).some,
    updatedTime = Some(toInstant("2017-11-11T00:10:08+0000")))

  "FacebookUser" should {
    s"be compatible with $userPath" in {
      decodeJson[FacebookUser](userPath) shouldBe user
    }
  }

}
