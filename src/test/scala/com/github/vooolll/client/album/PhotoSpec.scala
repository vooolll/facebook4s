package com.github.vooolll.client.album

import com.github.vooolll.base.FacebookClientSupport
import cats.implicits._

class PhotoSpec extends FacebookClientSupport {

  import com.github.vooolll.base.TestConfiguration._

  "Facebook Graph Api" should {
    "return photo" in { c =>
      c.photo(photoId) map (_.uriWithoutQueryParams shouldBe photo)
    }

    "return photo result" in { c =>
      c.photoResult(photoId) map (_.map(_.uriWithoutQueryParams) shouldBe photo.asRight)
    }
  }
}
