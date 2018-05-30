package com.github.vooolll.serialization.compatibility.album

import com.github.vooolll.domain.FacebookPaging
import com.github.vooolll.domain.albums.{FacebookAlbum, FacebookAlbumId, FacebookAlbums}
import com.github.vooolll.serialization.FacebookDecoders._
import com.github.vooolll.serialization.compatibility.CompatibilitySpec
import com.github.vooolll.base._

class AlbumCompatSpec extends CompatibilitySpec {
  val albumsPath = "testdata/albums.json"

  val facebookCoverAlbum = FacebookAlbum(FacebookAlbumId("120118722114158"), "Cover Photos",
    toInstant("2017-12-19T14:08:44+0000"))

  val facebookAlbums = FacebookAlbums(
    List(facebookCoverAlbum, facebookProfileAlbum),
    Some(FacebookPaging(
      Some("MTIwMTE4NzIyMTE0MTU4"),
      Some("MTE3NjA3MjM1Njk4NjQw"))))

  "FacebookAlbum" should {
    s"be compatible with $albumsPath" in {
      decodeJson[FacebookAlbums](albumsPath) shouldBe facebookAlbums
    }
  }
}
