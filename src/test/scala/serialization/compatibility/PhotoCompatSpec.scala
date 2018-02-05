package serialization.compatibility

import base.{JsonSerializationSupport, SyncSpec}
import domain.albums.FacebookAlbum
import serialization.FacebookDecoders._
import domain.albums.image.FacebookImage

class PhotoCompatSpec extends SyncSpec with JsonSerializationSupport {

  val photoImagePath = "testdata/photo_image.json"
  val albumPath = "testdata/photo_album.json"

  "FacebookImage" should {
    s"be compatible with $photoImagePath" in {
      decodeJson[FacebookImage](photoImagePath) map(_ shouldBe facebookImage)
    }
  }

  "FacebookAlbum" should {
    s"be compatible with $albumPath" in {
      decodeJson[FacebookAlbum](albumPath) map(_ shouldBe facebookAlbum)
    }
  }

}
