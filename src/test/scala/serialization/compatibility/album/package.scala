package serialization.compatibility

import domain.albums.{FacebookAlbum, FacebookAlbumId}
import base._

package object album {
  val facebookProfileAlbum = FacebookAlbum(FacebookAlbumId("117607235698640"), "Profile Pictures", toInstant("2017-12-18T11:30:10+0000"))
}
