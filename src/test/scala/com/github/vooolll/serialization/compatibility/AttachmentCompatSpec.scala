package com.github.vooolll.serialization.compatibility

import java.net.URL

import com.github.vooolll.domain.media._
import io.circe._
import io.circe.generic.semiauto._
import com.github.vooolll.serialization.FacebookDecoders._
import cats.implicits._

class AttachmentCompatSpec extends CompatibilitySpec {

  case class TestObject(attachmentType: AttachmentType)

  implicit val decodeTestObject: Decoder[TestObject] = deriveDecoder[TestObject]

  val attachmentTargetPath = "testdata/attachment_target.json"
  val attachmentImagePath  = "testdata/attachment_image.json"
  val attachmentPath       = "testdata/comment_attachment.json"

  val imageSource = FacebookImageSource(
    720,
    new URL(
      "https://scontent.xx.fbcdn.net/v/t1.0-9/26169805_135224317270265_2857586441485590537_n.jpg" +
        "?oh=97edfd66290b3e4112a8731e8cd2b5fb&oe=5B0A23AC"
    ),
    104
  )

  val attachmentTarget = FacebookAttachmentTarget(
    Some(FacebookAttachmentId("135224317270265")),
    Some(
      new URL(
        "https://www.facebook.com/photo.php?fbid=135224317270265&set=p.135224317270265&type=3"
      )
    )
  )

  val attachmentTypes = Map(
    "video_inline" -> AttachmentTypes.Video,
    "photo" -> AttachmentTypes.Photo,
    "sticker" -> AttachmentTypes.Sticker,
    "animated_image_autoplay" -> AttachmentTypes.GIF,
    "cover_photo" -> AttachmentTypes.CoverPhoto,
    "profile_media" -> AttachmentTypes.ProfileMedia,
    "life_event" -> AttachmentTypes.LifeEvent,
    "map" -> AttachmentTypes.Map,
    "fun_fact_stack" -> AttachmentTypes.FunFactStack
  )

  val facebookAttachment = FacebookAttachment(
    imageSource.some,
    attachmentTarget,
    attachmentTarget.url,
    AttachmentTypes.Photo,
    "photo".some
  )

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

    for {
      (value, attachmentType) <- attachmentTypes
    } {
      s"attachment type $attachmentType should be compatible with $value" in {
        decodeType(value) shouldBe TestObject(attachmentType)
      }
    }
  }

  private[this] def decodeType(attachmentTypeRaw: String) = {
    decodeStringJson[TestObject](
      "{ \"attachmentType\": \"" + attachmentTypeRaw + "\"}"
    )
  }
}
