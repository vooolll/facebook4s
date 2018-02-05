package domain.albums.photo

import java.time.Instant

import domain.albums.FacebookAlbum
import domain.albums.image.FacebookImage

case class FacebookPhoto(
  id          : String,
  createdTime : Option[Instant],
  images      : Seq[FacebookImage],
  album       : Option[FacebookAlbum])