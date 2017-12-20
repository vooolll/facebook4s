package serialization

import java.time.{Instant, ZoneOffset}

import config.FacebookConstants.dateFormat
import domain.feed.{FacebookFeed, FacebookPaging}
import domain.likes.{FacebookLike, FacebookLikeId, FacebookLikes, FacebookLikesPaging}
import domain.oauth._
import domain.posts.{FacebookPost, FacebookPostId}
import domain.profile._
import org.apache.commons.lang3.LocaleUtils
import cats.implicits._

import scala.concurrent.duration._

package object compatibility {

  val userId = FacebookUserId("499313270413277")

  val postId = FacebookPostId("499313270413277_504668796544391")

  val post = FacebookPost(
    id = FacebookPostId("499313270413277_504668796544391"),
    story = "Valeryi Baibossynov updated his profile picture.".some,
    createdTime = Some(toInstant("2017-10-01T13:43:05+0000")),
    objectId = "513792128965391".some,
    picture = "picture link".some,
    from = FacebookProfileId("499313270413277").some
  )

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
      FacebookPost(FacebookPostId("499313270413277_504668796544391"), "Valeryi Baibossynov updated his profile picture.".some,
        Some(toInstant("2017-10-01T13:43:05+0000")), None, None, None),

      FacebookPost(FacebookPostId("499313270413277_139299253081349"),
        "Valeryi Baibossynov added a life event from May 2, 1993: Born on May 2, 1993.".some,
        Some(toInstant("1993-05-02T07:00:00+0000")), None, None, None)
    ),
    FacebookPaging("https://graph.facebook.com1".some, "https://graph.facebook.com".some))


  val like = FacebookLike(FacebookLikeId("215080582368050"), "Яна Чиркова".some)
  val likesPaging = FacebookLikesPaging("MTkzMDAwNzk1MDU5NTAzOAZDZD".some, "MjE1MDgwNTgyMzY4MDUw".some)
  val likes = FacebookLikes(List(like), likesPaging)

  def toInstant(string: String) = dateFormat.parse(string, Instant.from(_))

}
