package com.github.vooolll.client

import java.net.URL

import com.github.vooolll.base.FacebookClientSupport
import cats.syntax.either._
import com.github.vooolll.domain.oauth.{FacebookAppId, FacebookClientId, FacebookError}
import com.github.vooolll.domain.profile.FacebookApplication

class ApplicationSpec extends FacebookClientSupport {

  import com.github.vooolll.base.TestConfiguration._

  val appId    = FacebookAppId("1970529913214515")
  val clientId = FacebookClientId(appId.value)
  val application = FacebookApplication(
    appId,
    new URL("http://localhost:9000/redirect"),
    "testing_app"
  )

  "Facebook Graph Api" should {
    "get application by id" in { c =>
      c.application(appId) map (_ shouldBe application)
    }

    "get application result by id" in { c =>
      c.applicationResult(clientId) map (_ shouldBe application.asRight)
    }

    "return error InvalidVerificationCodeFormat" in { c =>
      c.applicationResult(clientId.copy("wrong id")) map {
        case Right(_) => fail("bad request expected")
        case Left(e) =>
          e.errorType shouldBe FacebookError.SpecifiedObjectNotFound
      }
    }
  }
}
