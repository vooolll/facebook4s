package serialization.compatibility

import base.{JsonSerializationSupport, SyncSpec}
import domain.albums.FacebookAlbums
import serialization.FacebookDecoders._

class AlbumCompatSpec extends SyncSpec with JsonSerializationSupport {
  val albumsPath = "testdata/albums.json"

  "FacebookAlbum" should {
    s"be compatible with $albumsPath" in {
      decodeJson[FacebookAlbums](albumsPath) shouldBe facebookAlbums
    }
  }
}
