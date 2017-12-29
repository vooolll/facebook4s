package services

import akka.http.scaladsl.model.StatusCodes
import base.AsyncSpec
import services.DomainParseService.FacebookAppResources

class AsyncRequestServiceSpec extends AsyncSpec {
  val appResources = new FacebookAppResources()
  val asyncRequestService = new AsyncRequestService()

  import appResources._
  implicit val ec = executionContext

  val uriService = UriService()
  "Should send request" in {
    asyncRequestService.sendRequest(uriService.appTokenUri)(actorSystem, materializer).map(_.status shouldBe StatusCodes.OK)
  }
}
