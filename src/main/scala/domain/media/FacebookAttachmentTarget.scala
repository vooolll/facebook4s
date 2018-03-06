package domain.media

import java.net.URL

final case class FacebookImageSource(height: Double, src: URL, width: Double)
final case class FacebookAttachmentId(value: String)
final case class FacebookAttachmentTarget(id: FacebookAttachmentId, url: URL)
