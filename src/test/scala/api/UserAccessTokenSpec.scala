package api

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, ResponseEntity}
import akka.stream.ActorMaterializer
import domain.AppAccessToken
import org.f100ded.scalaurlbuilder.URLBuilder
import org.scalatest.{AsyncWordSpec, Matchers, WordSpec}
import org.scalatest.mockito.MockitoSugar
import org.mockito.Matchers._
import org.mockito.Mockito._
import services.AsyncRequestService

import scala.concurrent.Future
import scala.io.Source

class UserAccessTokenSpec extends AsyncWordSpec with Matchers with MockitoSugar with MockedAsyncRequestService {

  "Facebook Graph Api" should {
    "return user access token" in {
      mockSendWithResource(resourcePath = "testdata/user_access_token.json")
      val client = FacebookClient(asyncRequestService)
      client.userAccessToken("code") map {
        case Right(token) =>
          token.valueToken.value shouldBe "test token"
          token.tokenType shouldBe AppAccessToken("bearer")
         case Left(e) => fail(e.error.message)
      }
    }
  }

}

trait MockedAsyncRequestService extends MockitoSugar {
  implicit val actorSystem = ActorSystem()
  val materializer = ActorMaterializer()

  val asyncRequestService = mock[AsyncRequestService]

  def mockSendWithResource(resourcePath: String) = {
    when(asyncRequestService.send(anyObject[URLBuilder])).thenReturn(
      Future.successful(
        HttpResponse(
          entity = HttpEntity(
            contentType = ContentTypes.`application/json`,
            string      = Source.fromResource(resourcePath).mkString)
        )
      )
    )
  }
  when(asyncRequestService.ec) thenReturn actorSystem.dispatcher
  when(asyncRequestService.materializer) thenReturn materializer
}
