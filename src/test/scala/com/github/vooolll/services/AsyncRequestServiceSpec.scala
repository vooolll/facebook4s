package com.github.vooolll.services

import akka.http.scaladsl.model.StatusCodes
import com.github.vooolll.base.{AsyncResourceSpec, TestUrls}

class AsyncRequestServiceSpec extends AsyncResourceSpec {
  val asyncRequest = AsyncRequest()

  "Should send GET request" in {
    val responseContext = asyncRequest(TestUrls.appTokenUri)
    responseContext.cleanResources()
    responseContext.response.map(_.status shouldBe StatusCodes.OK)
  }

  "Should send POST request" in {
    val responseContext =
      asyncRequest.post(TestUrls.appTokenUri, Map("test" -> "value"))
    responseContext.cleanResources()
    responseContext.response.map(_.status shouldBe StatusCodes.OK)
  }

}
