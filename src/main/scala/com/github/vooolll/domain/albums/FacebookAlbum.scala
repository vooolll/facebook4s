package com.github.vooolll.domain.albums

import java.time.Instant
import com.github.vooolll.domain.FacebookPaging

final case class FacebookAlbumId(value: String) extends AnyVal

final case class FacebookAlbum(id: FacebookAlbumId, name: String, createdTime: Instant)

final case class FacebookAlbums(albums: List[FacebookAlbum], paging: Option[FacebookPaging])
