package com.github.vooolll.serialization.compatibility

import java.net.URL

import com.github.vooolll.domain.profile._
import com.github.vooolll.serialization.FacebookDecoders._
import cats.implicits._

class UserCompatSpec extends CompatibilitySpec {

  val userPath = "testdata/basic_user.json"

  val user = FacebookUser(
    id        = FacebookUserId("499313270413277"),
    email     = "potnyiakk@gmail.com".some,
    name      = "Valeryi Baibossynov".some,
    lastName  = "Baibossynov".some,
    firstName = "Valeryi".some,
    link = new URL(
      "https://www.facebook.com/app_scoped_user_id/499313270413277/"
    ).some,
    picture = FacebookUserPicture(
      height       = 50,
      isSilhouette = false,
      url          = new URL("http://example.com"),
      wight        = 50
    ).some,
    gender   = Gender.Male.some,
    ageRange = AgeRange(21, None).some,
    hometown = FacebookTown(id = "115486911798138", name = "Almaty, Kazakhstan").some,
    location = FacebookTown(id = "115486911792222", name = "Rome, Italy").some
  )

  "FacebookUser" should {
    s"be compatible with $userPath" in {
      decodeJson[FacebookUser](userPath) shouldBe user
    }
  }

}
