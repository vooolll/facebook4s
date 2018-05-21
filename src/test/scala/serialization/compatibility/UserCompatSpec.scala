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
    link        = new URL("https://www.facebook.com/app_scoped_user_id/499313270413277/").some,
    picture     = FacebookUserPicture(
      height       = 50,
      isSilhouette = false,
      url          = new URL("http://example.com"),
      wight        = 50).some,
    gender      = Gender.Male.some,
    ageRange    = AgeRange(21, None).some)

  "FacebookUser" should {
    s"be compatible with $userPath" in {
      decodeJson[FacebookUser](userPath) shouldBe user
    }
  }

}
