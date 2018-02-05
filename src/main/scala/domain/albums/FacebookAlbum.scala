package domain.albums

import java.time.Instant

final case class FacebookAlbum(id: String, name: String, createdTime: Instant)