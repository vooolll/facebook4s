package serialization.compatibility

import base.{JsonSerializationSupport, SyncSpec}
import domain.albums.FacebookAlbum
import serialization.FacebookDecoders._
import domain.albums.image.FacebookImage
import domain.albums.photo.FacebookPhoto

class PhotoCompatSpec extends SyncSpec with JsonSerializationSupport {

  val imagePath = "testdata/photo_image.json"
  val albumPath = "testdata/photo_album.json"
  val photoPath = "testdata/photo.json"

  "FacebookImage" should {
    s"be compatible with $imagePath" in {
      decodeJson[FacebookImage](imagePath) map(_ shouldBe facebookImage)
    }
  }

  "FacebookAlbum" should {
    s"be compatible with $albumPath" in {
      decodeJson[FacebookAlbum](albumPath) map(_ shouldBe facebookAlbum)
    }
  }

  "FacebookPhoto" should {
    s"be compatible with $photoPath" in {
      decodeJson[FacebookPhoto](albumPath) map(_ shouldBe facebookPhoto)
    }
  }

}
