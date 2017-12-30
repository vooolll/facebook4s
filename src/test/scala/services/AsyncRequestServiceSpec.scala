package services

import akka.http.scaladsl.model.StatusCodes
import base.AsyncSpec

class AsyncRequestServiceSpec extends AsyncSpec {
  implicit val appResources = FacebookAppResources()
  val asyncRequestService = AsyncRequestService()
  
  implicit val ec = executionContext

  val uriService = UriService()
  "Should send request" in {
    asyncRequestService.sendRequest(uriService.appTokenUri).map(_.status shouldBe StatusCodes.OK)
  }
}
