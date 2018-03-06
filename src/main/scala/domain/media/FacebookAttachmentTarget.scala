package domain.media

import java.net.URL

final case class FacebookAttachmentId(value: String)
case class FacebookAttachmentTarget(id: FacebookAttachmentId, url: URL)
