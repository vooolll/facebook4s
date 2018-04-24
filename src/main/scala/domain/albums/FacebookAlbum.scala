package domain.albums

import java.time.Instant
import domain.FacebookPaging

final case class FacebookAlbumId(value: String)

final case class FacebookAlbum(id: FacebookAlbumId, name: String, createdTime: Instant)

final case class FacebookAlbums(albums: List[FacebookAlbum], paging: Option[FacebookPaging])