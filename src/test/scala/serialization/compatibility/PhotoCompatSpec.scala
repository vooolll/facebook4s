package serialization.compatibility

import base.{JsonSerializationSupport, SyncSpec}
import serialization.FacebookDecoders._
import domain.albums.image.FacebookImage

class PhotoCompatSpec extends SyncSpec with JsonSerializationSupport {

  val photoImagePath = "testdata/photo_image.json"

  "Photo" should {
    s"be compatible with $photoImagePath" in {
      decodeJson[FacebookImage](photoImagePath) map(_ shouldBe facebookImage)
    }
  }

}
