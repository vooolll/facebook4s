package serialization.compatibility

import base.{JsonSerializationSupport, SyncSpec}
import domain.media._
import io.circe._
import io.circe.generic.semiauto._
import serialization.FacebookDecoders._

class AttachmentCompatSpec extends SyncSpec with JsonSerializationSupport {

  case class TestObject(attachmentType: AttachmentType)

  implicit val decodeTestObject: Decoder[TestObject] = deriveDecoder[TestObject]

  val attachmentTargetPath = "testdata/attachment_target.json"
  val attachmentImagePath = "testdata/attachment_image.json"
  val attachmentPath = "testdata/comment_attachment.json"

  "FacebookAttachmentTarget" should {
    s"be compatible with $attachmentTargetPath" in {
      decodeJson[FacebookAttachmentTarget](attachmentTargetPath) shouldBe attachmentTarget
    }

    s"be compatible with $attachmentImagePath" in {
      decodeJson[FacebookImageSource](attachmentImagePath) shouldBe imageSource
    }

    s"be compatible with $attachmentPath" in {
      decodeJson[FacebookAttachment](attachmentPath) shouldBe facebookAttachment
    }

    s"attachment types should be compatible" in {
      decodeType("video_inline") shouldBe TestObject(AttachmentTypes.Video)
      decodeType("photo") shouldBe TestObject(AttachmentTypes.Photo)
      decodeType("sticker") shouldBe TestObject(AttachmentTypes.Sticker)
      decodeType("animated_image_autoplay") shouldBe TestObject(AttachmentTypes.GIF)
    }
  }

  private def decodeType(attachmentTypeRaw: String) = {
    decodeStringJson[TestObject]("{ \"attachmentType\": \"" + attachmentTypeRaw + "\"}")
  }
}
