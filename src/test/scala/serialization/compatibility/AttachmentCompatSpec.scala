package serialization.compatibility

import base.{JsonSerializationSupport, SyncSpec}
import domain.media.FacebookAttachmentTarget
import serialization.FacebookDecoders.decodeAttachmentTarget

class AttachmentCompatSpec extends SyncSpec with JsonSerializationSupport {

  val attachmentTargetPath = "testdata/attachment_target.json"

  "FacebookAttachmentTarget" should {
    s"be compatible with $attachmentTargetPath" in {
      decodeJson[FacebookAttachmentTarget](attachmentTargetPath) shouldBe attachmentTarget
    }
  }
}
