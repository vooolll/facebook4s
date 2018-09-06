package com.github.vooolll.services

import akka.http.scaladsl.model.StatusCodes
import com.github.vooolll.base.{AsyncResourceSpec, TestUrls}

class AsyncRequestServiceSpec extends AsyncResourceSpec {
  val asyncRequest = AsyncRequest()

  "Should send request" in {
    val (_, request) = asyncRequest(TestUrls.appTokenUri)
    request.map(_.status shouldBe StatusCodes.OK)
  }
}
