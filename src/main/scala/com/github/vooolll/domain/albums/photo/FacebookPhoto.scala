package com.github.vooolll.domain.albums.photo

import java.time.Instant

import com.github.vooolll.domain.albums.FacebookAlbum
import com.github.vooolll.domain.albums.image.FacebookImage

final case class FacebookPhotoId(value: String)

final case class FacebookPhoto(
  id          : FacebookPhotoId,
  createdTime : Option[Instant],
  images      : List[FacebookImage],
  album       : Option[FacebookAlbum])