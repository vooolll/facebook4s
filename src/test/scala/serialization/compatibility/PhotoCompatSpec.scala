package serialization.compatibility

import domain.albums.FacebookAlbum
import domain.albums.image.FacebookImage
import domain.albums.photo.FacebookPhoto
import serialization.FacebookDecoders._

class PhotoCompatSpec extends CompatibilitySpec {

  val imagePath = "testdata/photo_image.json"
  val albumPath = "testdata/photo_album.json"
  val photoPath = "testdata/photo.json"

  "FacebookPhoto" should {
    s"be compatible with $imagePath" in {
      decodeJson[FacebookImage](imagePath) shouldBe facebookImage
    }
  }

  "FacebookAlbum" should {
    s"be compatible with $albumPath" in {
      decodeJson[FacebookAlbum](albumPath) shouldBe facebookProfileAlbum
    }
  }

  "FacebookPhoto" should {
    s"be compatible with $photoPath" in {
      decodeJson[FacebookPhoto](photoPath) shouldBe facebookPhoto
    }
  }

}
