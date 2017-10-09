package services

import akka.http.scaladsl.model.StatusCodes
import org.scalatest.concurrent.Eventually
import org.scalatest.mockito.MockitoSugar
import org.scalatest.time.{Millis, Span}
import org.scalatest.{AsyncWordSpec, Matchers}

class AsyncRequestServiceSpec extends AsyncWordSpec with Matchers with MockitoSugar with Eventually
  with ApplicationResources {

  implicit val patience = PatienceConfig(timeout = scaled(Span(2000, Millis)))

  val asyncRequestService = new AsyncRequestService()(system, mat, ec)


  val uriService = UriService()
  "Should send request" in {
    asyncRequestService.sendRequest(uriService.appTokenUri).map(_.status shouldBe StatusCodes.OK)
  }

//  "Should close actor system after unmarshaling to specified type" in {
//    sendRequest(uriService.appTokenUri)(facebookAppAccessTokenReads) flatMap { _ =>
//      val terminatedCallback = system.whenTerminated
//      eventually {
//        Future.successful(terminatedCallback.isCompleted shouldBe true)
//      }
//    }
//  }
//
//  private def sendRequest[A](uri: URLBuilder)(reads: Reads[A]) = {
//    asyncRequestService.sendAndParseTo(uri)(reads, facebookLoginErrorReads)(loginErrorFE)
//  }
}
