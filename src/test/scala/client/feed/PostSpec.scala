package client.feed

import base.FacebookClientSupport
import cats.implicits._
import base.TestConfiguration._
import domain.oauth.FacebookError

class PostSpec extends FacebookClientSupport {

  "Facebook Graph Api" should {
    "return posts" in { c =>
      c.post(postId, userAccessToken) map(_.withoutQueryParams shouldBe post)
    }

    "return posts result" in { c =>
      c.postResult(postId, userAccessToken) map(p => p.map(_.withoutQueryParams) shouldBe post.asRight)
    }

    "return error SpecifiedObjectNotFound" in { c =>
      c.postResult(postId.copy(value = "wrong id"), userAccessToken).map {
        case Right(_) => fail("should return error")
        case Left(e) => e.errorType shouldBe FacebookError.SpecifiedObjectNotFound
      }
    }

    "return error specified InvalidVerificationCodeFormat" in { c =>
      c.postResult(postId.copy(value = "wrong"), userAccessToken).map {
        case Right(_) => fail("should return error")
        case Left(e) => e.errorType shouldBe FacebookError.InvalidVerificationCodeFormat
      }
    }
  }

}
