package serialization.compatibility

import java.time.Instant

import cats.syntax.option._
import config.FacebookConstants
import domain.feed.{FacebookFeed, FacebookPaging, FacebookSimplePost}
import domain.oauth._
import FacebookConstants._
import domain.profile.{FacebookApplication, FacebookUser, FacebookUserId, FacebookUserPicture}

import scala.concurrent.duration._

object TestEntities {

  val userId = FacebookUserId("499313270413277")

  val userPicture = FacebookUserPicture(50, isSilhouette = false, "image url", 50)
  val user = FacebookUser(userId, "Valeryi Baibossynov".some, userPicture.some)

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
      FacebookSimplePost("499313270413277_504668796544391", "Valeryi Baibossynov updated his profile picture.",
        toInstant("2017-10-01T13:43:05+0000")),
      FacebookSimplePost("499313270413277_139299253081349",
        "Valeryi Baibossynov added a life event from May 2, 1993: Born on May 2, 1993.",
        toInstant("1993-05-02T07:00:00+0000"))
    ),
    FacebookPaging("https://graph.facebook.com1".some, "https://graph.facebook.com".some))


  def toInstant(string: String) = dateFormat.parse(string, Instant.from(_))

}
