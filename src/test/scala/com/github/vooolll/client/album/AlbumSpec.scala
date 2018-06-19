package com.github.vooolll.client.album

import com.github.vooolll.base.FacebookClientSupport
import cats.implicits._

class AlbumSpec extends FacebookClientSupport {

  import com.github.vooolll.base.TestConfiguration._

  "Facebook Graph Api" should {
    "return album" in { c =>
      c.albums(profileId) map (_ shouldBe facebookAlbums)
    }

    "return album result" in { c =>
      c.albumsResult(profileId) map (_ shouldBe facebookAlbums.asRight)
    }
  }
}
