package services

import akka.http.scaladsl.model.StatusCodes
import org.scalatest.concurrent.Eventually
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncWordSpec, Matchers}

class AsyncRequestServiceSpec extends AsyncWordSpec with Matchers with MockitoSugar with Eventually
  with ApplicationResources {

  val asyncRequestService = new AsyncRequestService()(system, mat, ec)

  val uriService = UriService()
  "Should send request" in {
    asyncRequestService.sendRequest(uriService.appTokenUri).map(_.status shouldBe StatusCodes.OK)
  }
}
