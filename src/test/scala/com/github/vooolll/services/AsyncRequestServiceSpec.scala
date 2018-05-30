package com.github.vooolll.services

import akka.http.scaladsl.model.StatusCodes
import com.github.vooolll.base.{AsyncSpec, TestUrls}
import org.scalatest.Tag

import scala.concurrent.ExecutionContext

class AsyncRequestServiceSpec extends AsyncSpec {
  implicit val appResources: FacebookAppResources = FacebookAppResources()
  val asyncRequest = AsyncRequest()

  implicit val ec: ExecutionContext = executionContext

  "Should send request" taggedAs Tag("slow") in {
    asyncRequest(TestUrls.appTokenUri).map(_.status shouldBe StatusCodes.OK)
  }
}
