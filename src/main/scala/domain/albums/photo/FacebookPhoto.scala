package domain.albums.photo

import java.time.Instant

import domain.albums.FacebookAlbum
import domain.albums.image.FacebookImage

final case class FacebookPhotoId(value: String)

final case class FacebookPhoto(
  id          : FacebookPhotoId,
  createdTime : Option[Instant],
  images      : List[FacebookImage],
  album       : Option[FacebookAlbum])