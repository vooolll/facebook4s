package serialization.compatibility

import java.time._

import cats.syntax.option._
import config.FacebookConstants
import domain.feed.{FacebookFeed, FacebookPaging}
import domain.oauth._
import FacebookConstants._
import domain.posts.{FacebookPost, FacebookPostId}
import domain.profile._
import org.apache.commons.lang3.LocaleUtils

import scala.concurrent.duration._

object TestEntities {

  val userId = FacebookUserId("499313270413277")

  val postId = FacebookPostId("499313270413277_504668796544391")

  val post = FacebookPost(FacebookPostId("499313270413277_504668796544391"), "Valeryi Baibossynov updated his profile picture.",
    toInstant("2017-10-01T13:43:05+0000"))

  val userPicture = FacebookUserPicture(50, isSilhouette = false, "image url", 50)
  val user = FacebookUser(
    id          = userId,
    name        = "Valeryi Baibossynov".some,
    lastName    = "Baibossynov".some,
    firstName   = "Valeryi".some,
    verified    = true.some,
    link        = "https://www.facebook.com/app_scoped_user_id/499313270413277/".some,
    picture     = userPicture.some,
    locale      = LocaleUtils.toLocale("en_US").some,
    timezone    = ZoneOffset.ofHours(2).some,
    gender      = Gender.Male.some,
    ageRange    = AgeRange(21, None).some,
    cover       = Cover("527696177574986", 0, 0, "link").some,
    updatedTime = Some(toInstant("2017-11-11T00:10:08+0000")))

  val appId = FacebookAppId("1969406143275709")
  val facebookClientId = FacebookClientId("1969406143275709")

  val userAccessToken = FacebookAccessToken(
    TokenValue("token"), UserAccessToken("bearer", 5107587.seconds))

  val appAccessToken = FacebookAccessToken(
    TokenValue("1234567891011121|A6BCDEFiGASDFdB1_Zviht7lzxc"), AppAccessToken("bearer"))

  val clientCode = FacebookClientCode("test-test-test-test", "machine id".some)
  val application = FacebookApplication(FacebookAppId("1969406143275709"), "https://www.facebook.com/games/?app_id=1969406143275709",
    "testing_app")

  val facebookOauthError = FacebookOauthError(FacebookError("Invalid verification code format."))

  val feed = FacebookFeed(
    List(
      FacebookPost(FacebookPostId("499313270413277_504668796544391"), "Valeryi Baibossynov updated his profile picture.",
        toInstant("2017-10-01T13:43:05+0000")),
      FacebookPost(FacebookPostId("499313270413277_139299253081349"),
        "Valeryi Baibossynov added a life event from May 2, 1993: Born on May 2, 1993.",
        toInstant("1993-05-02T07:00:00+0000"))
    ),
    FacebookPaging("https://graph.facebook.com1".some, "https://graph.facebook.com".some))


  def toInstant(string: String) = dateFormat.parse(string, Instant.from(_))

}
