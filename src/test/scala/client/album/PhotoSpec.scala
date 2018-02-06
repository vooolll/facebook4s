package client.album

import base.FacebookClientSupport
import base.TestConfiguration.userTokenRaw
import cats.implicits._
import domain.albums.image.FacebookImage
import domain.albums.photo.{FacebookPhoto, FacebookPhotoId}
import domain.albums.{FacebookAlbum, FacebookAlbumId}
import serialization.compatibility._

class PhotoSpec extends FacebookClientSupport {

  val photoId = FacebookPhotoId("120118675447496")

  val source = "25398995_120118675447496_5830741756468130361_n.jpg"

  val album = FacebookAlbum(FacebookAlbumId("120118722114158"), "Cover Photos", toInstant("2017-12-19T14:08:44+0000"))
  val photo = FacebookPhoto(
    photoId,
    Some(toInstant("2017-12-19T14:08:45+0000")),
    List(FacebookImage(400, source, 800), FacebookImage(320, source, 640), FacebookImage(130, source, 260),
      FacebookImage(225, source, 450)),
    Some(album)
  )

  "Facebook Graph Api" should {
    "return user profile" in { c =>
      c.photo(photoId, userTokenRaw) map (_.uriWithoutQueryParams shouldBe photo)
    }

    "return user profile result" in { c =>
      c.photoResult(photoId, userTokenRaw) map (_.map(_.uriWithoutQueryParams) shouldBe photo.asRight)
    }
  }
}
