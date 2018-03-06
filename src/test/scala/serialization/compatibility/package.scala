package serialization

import java.net.URL
import java.time.{Instant, ZoneOffset}

import config.FacebookConstants.dateFormat
import domain.feed.{FacebookFeed, FacebookFeedPaging}
import domain.likes.{FacebookLike, FacebookLikes, FacebookLikesSummary, FacebookPaging}
import domain.oauth._
import domain.posts.{FacebookPost, FacebookPostId}
import domain.profile._
import org.apache.commons.lang3.LocaleUtils
import cats.implicits._
import domain.FacebookOrder
import domain.albums.{FacebookAlbum, FacebookAlbumId}
import domain.albums.image.FacebookImage
import domain.albums.photo.{FacebookPhoto, FacebookPhotoId}
import domain.comments._
import domain.media.{FacebookAttachmentId, FacebookAttachmentTarget}

import scala.concurrent.duration._

package object compatibility {

  val userId = FacebookUserId("499313270413277")

  val postId = FacebookPostId("499313270413277_504668796544391")

  val post = FacebookPost(
    id = FacebookPostId("499313270413277_504668796544391"),
    story = "Valeryi Baibossynov updated his profile picture.".some,
    createdTime = Some(toInstant("2017-10-01T13:43:05+0000")),
    objectId = "513792128965391".some,
    picture = new URL("http://example.com").some,
    from = FacebookProfileId("499313270413277").some
  )

  val userPicture = FacebookUserPicture(50, isSilhouette = false, new URL("http://example.com"), 50)
  val user = FacebookUser(
    id          = userId,
    name        = "Valeryi Baibossynov".some,
    lastName    = "Baibossynov".some,
    firstName   = "Valeryi".some,
    verified    = true.some,
    link        = new URL("https://www.facebook.com/app_scoped_user_id/499313270413277/").some,
    picture     = userPicture.some,
    locale      = LocaleUtils.toLocale("en_US").some,
    timezone    = ZoneOffset.ofHours(2).some,
    gender      = Gender.Male.some,
    ageRange    = AgeRange(21, None).some,
    cover       = Cover("527696177574986", 0, 0, new URL("http://example.com")).some,
    updatedTime = Some(toInstant("2017-11-11T00:10:08+0000")))

  val appId = FacebookAppId("1969406143275709")
  val facebookClientId = FacebookClientId("1969406143275709")

  val userAccessToken = FacebookAccessToken(
    TokenValue("token"), UserAccessToken("bearer", 5107587.seconds))

  val appAccessToken = FacebookAccessToken(
    TokenValue("1234567891011121|A6BCDEFiGASDFdB1_Zviht7lzxc"), AppAccessToken("bearer"))

  val clientCode = FacebookClientCode("test-test-test-test", "machine id".some)
  val application = FacebookApplication(FacebookAppId("1969406143275709"),
    new URL("https://www.facebook.com/games/?app_id=1969406143275709"),
    "testing_app")

  val facebookError = FacebookError("Invalid verification code format.",
    FacebookError.InvalidVerificationCodeFormat)

  val feed = FacebookFeed(
    List(
      FacebookPost(FacebookPostId("499313270413277_504668796544391"), "Valeryi Baibossynov updated his profile picture.".some,
        Some(toInstant("2017-10-01T13:43:05+0000")), None, None, None),

      FacebookPost(FacebookPostId("499313270413277_139299253081349"),
        "Valeryi Baibossynov added a life event from May 2, 1993: Born on May 2, 1993.".some,
        Some(toInstant("1993-05-02T07:00:00+0000")), None, None, None)
    ),
    FacebookFeedPaging("https://graph.facebook.com1".some, "https://graph.facebook.com".some))


  val likesSummary = FacebookLikesSummary(totalCount = 1, canLike = true.some, hasLikes = true.some)

  val like = FacebookLike(FacebookUserId("215080582368050"), "Iana Baibossynova".some)

  val likesPaging = FacebookPaging("MTkzMDAwNzk1MDU5NTAzOAZDZD".some, "MjE1MDgwNTgyMzY4MDUw".some)

  val likes = FacebookLikes(List(like), likesPaging)

  val comment = FacebookComment(
    id = FacebookCommentId("120118675447496_128078554651508"),
    message = "Super comment".some,
    createdTime = Some(toInstant("2017-12-25T10:23:54+0000")),
    from = FacebookProfileId("117661112359919").some,
    None)

  val commentPaging = FacebookPaging(
    "WTI5dGJXVnVkRjlqZAFhKemIzSTZANVEk0TURjNE5UVTBOalV4TlRBNE9qRTFNVFF4T1RjME16UT0ZD".some,
    "WTI5dGJXVnVkRjlqZAFhKemIzSTZANVEk0TURjNE5UVTBOalV4TlRBNE9qRTFNVFF4T1RjME16UT0ZD".some)

  val comments = FacebookComments(List(comment), commentPaging.some)

  val commentSummary = FacebookCommentSummary(
    order = FacebookOrder.Chronological,
    totalCount = 1,
    canComment = true.some)

  val facebookImage = FacebookImage(225,
    new URL("https://scontent.xx.fbcdn.net/v/t1.0-0/p75x225/25396081_117607225698641_6348338142026249400_n.jpg"),
    450)

  val facebookAlbum = FacebookAlbum(FacebookAlbumId("117607235698640"), "Profile Pictures", toInstant("2017-12-18T11:30:10+0000"))

  val facebookPhoto = FacebookPhoto(
    FacebookPhotoId("117607225698641"), Some(toInstant("2017-12-18T11:30:11+0000")), List(facebookImage), facebookAlbum.some
  )

  val attachmentTarget = FacebookAttachmentTarget(FacebookAttachmentId("135224317270265"),
    new URL("https://www.facebook.com/photo.php?fbid=135224317270265&set=p.135224317270265&type=3"))

  def toInstant(string: String) = dateFormat.parse(string, Instant.from(_))

}
