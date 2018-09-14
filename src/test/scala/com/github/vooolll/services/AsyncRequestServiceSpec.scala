package com.github.vooolll.services

import akka.http.scaladsl.model.StatusCodes
import com.github.vooolll.base.{AsyncResourceSpec, TestUrls}

class AsyncRequestServiceSpec extends AsyncResourceSpec {
  val asyncRequest = AsyncRequest()

  "Should send GET request" in {
    val (http, request) = asyncRequest(TestUrls.appTokenUri)
    http.shutdownAllConnectionPools()
    request.map(_.status shouldBe StatusCodes.OK)
  }

  "Should send POST request" in {
    val (http, request) = asyncRequest.post(TestUrls.appTokenUri, Map("test" -> "value"))
    http.shutdownAllConnectionPools()
    request.map(_.status shouldBe StatusCodes.OK)
  }
}
