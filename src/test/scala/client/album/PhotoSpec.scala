package client.album

import base.FacebookClientSupport
import base.TestConfiguration.userTokenRaw
import cats.implicits._

class PhotoSpec extends FacebookClientSupport {

  "Facebook Graph Api" should {
    "return photo" in { c =>
      c.photo(photoId, userTokenRaw) map (_.uriWithoutQueryParams shouldBe photo)
    }

    "return photo result" in { c =>
      c.photoResult(photoId, userTokenRaw) map (_.map(_.uriWithoutQueryParams) shouldBe photo.asRight)
    }
  }
}
