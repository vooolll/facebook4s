package domain.albums

import java.time.Instant

final case class FacebookAlbumId(value: String)

final case class FacebookAlbum(id: FacebookAlbumId, name: String, createdTime: Instant)