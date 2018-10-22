package com.github.vooolll.domain.media

import java.net.URL

sealed trait AttachmentType

object AttachmentTypes {
  case object Photo extends AttachmentType
  case object Video extends AttachmentType
  case object GIF extends AttachmentType
  case object Sticker extends AttachmentType
  case object CoverPhoto extends AttachmentType
  case object ProfileMedia extends AttachmentType
  case object LifeEvent extends AttachmentType
  case object FunFactStack extends AttachmentType
  case object Map extends AttachmentType
  case object UnknowAttachmentType extends AttachmentType
}

final case class FacebookAttachment(
  attachment    : Option[FacebookImageSource], // TODO facebook4s-130 Rename to media
  target        : FacebookAttachmentTarget,
  url           : Option[URL],
  attachmentType: AttachmentType,
  title         : Option[String])

final case class FacebookImageSource(height: Double, src: URL, width: Double)
final case class FacebookAttachmentId(value: String) extends AnyVal
final case class FacebookAttachmentTarget(id: FacebookAttachmentId, url: Option[URL])
