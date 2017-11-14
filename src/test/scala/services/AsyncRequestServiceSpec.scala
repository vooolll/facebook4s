package services

import akka.http.scaladsl.model.StatusCodes
import client.ApplicationResources
import org.scalatest.concurrent.Eventually
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncWordSpec, Matchers}

class AsyncRequestServiceSpec extends AsyncWordSpec with Matchers with MockitoSugar with Eventually
  with ApplicationResources {

  val asyncRequestService = new AsyncRequestService()

  val uriService = UriService()
  "Should send request" in {
    asyncRequestService.sendRequest(uriService.appTokenUri)(system,mat).map(_.status shouldBe StatusCodes.OK)
  }
}
