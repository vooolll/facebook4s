package serialization.compatibility

import domain.albums.FacebookAlbums
import serialization.FacebookDecoders._

class AlbumCompatSpec extends CompatibilitySpec {
  val albumsPath = "testdata/albums.json"

  "FacebookAlbum" should {
    s"be compatible with $albumsPath" in {
      decodeJson[FacebookAlbums](albumsPath) shouldBe facebookAlbums
    }
  }
}
