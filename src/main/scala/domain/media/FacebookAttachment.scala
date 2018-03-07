package domain.media

import java.net.URL

sealed trait AttachmentType

object AttachmentTypes {
  case object Photo extends AttachmentType
  case object Video extends AttachmentType
  case object GIF extends AttachmentType
  case object Sticker extends AttachmentType
}

final case class FacebookAttachment(
  attachment    : FacebookImageSource,
  target        : FacebookAttachmentTarget,
  url           : URL,
  attachmentType: AttachmentType)

final case class FacebookImageSource(height: Double, src: URL, width: Double)
final case class FacebookAttachmentId(value: String)
final case class FacebookAttachmentTarget(id: FacebookAttachmentId, url: URL)
