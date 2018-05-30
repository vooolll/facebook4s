package com.github.vooolll.client.album

import com.github.vooolll.base.FacebookClientSupport
import com.github.vooolll.base.TestConfiguration.userTokenRaw
import cats.implicits._

class AlbumSpec extends FacebookClientSupport {

  "Facebook Graph Api" should {
    "return album" in { c =>
      c.albums(profileId, userTokenRaw) map (_ shouldBe facebookAlbums)
    }

    "return album result" in { c =>
      c.albumsResult(profileId, userTokenRaw) map (_ shouldBe facebookAlbums.asRight)
    }
  }
}
