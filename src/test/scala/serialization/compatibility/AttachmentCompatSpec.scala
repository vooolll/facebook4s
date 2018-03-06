package serialization.compatibility

import base.{JsonSerializationSupport, SyncSpec}
import domain.media.{FacebookAttachmentTarget, FacebookImageSource}
import serialization.FacebookDecoders._

class AttachmentCompatSpec extends SyncSpec with JsonSerializationSupport {

  val attachmentTargetPath = "testdata/attachment_target.json"
  val attachmentImagePath = "testdata/attachment_image.json"

  "FacebookAttachmentTarget" should {
    s"be compatible with $attachmentTargetPath" in {
      decodeJson[FacebookAttachmentTarget](attachmentTargetPath) shouldBe attachmentTarget
    }

    s"be compatible with $attachmentImagePath" in {
      decodeJson[FacebookImageSource](attachmentImagePath) shouldBe imageSource
    }
  }
}
