package services

import akka.http.scaladsl.model.StatusCodes
import base.AsyncSpec
import services.DomainParseService.FacebookAppResources

class AsyncRequestServiceSpec extends AsyncSpec {
  implicit val appResources = new FacebookAppResources()
  val asyncRequestService = new AsyncRequestService()
  
  implicit val ec = executionContext

  val uriService = UriService()
  "Should send request" in {
    asyncRequestService.sendRequest(uriService.appTokenUri).map(_.status shouldBe StatusCodes.OK)
  }
}
