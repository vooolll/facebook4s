package api

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse}
import akka.stream.ActorMaterializer
import domain.{AppAccessToken, FacebookAppSecret, FacebookClientId}
import org.f100ded.scalaurlbuilder.URLBuilder
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncWordSpec, Matchers}
import services.{AsyncRequestService, FacebookInternalServices}
import config.FacebookConfig._

import scala.concurrent.Future
import scala.io.Source

class UserAccessTokenSpec
  extends AsyncWordSpec with Matchers with MockitoSugar with MockedAsyncRequestService {

  trait ClientProbe extends FacebookInternalServices {
    override val asyncRequestService = mockAsyncRequestService
    override val clientId = mock[FacebookClientId]
    override val appSecret = mock[FacebookAppSecret]
  }

  "Facebook Graph Api" should {
    "return user access token" in {
      mockSendWithResource(resourcePath = "testdata/user_access_token.json")
      val client = new FacebookClient(clientId, appSecret) with ClientProbe
      client.userAccessTokenEither("code") map {
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

  val facebookServices = mock[FacebookInternalServices]
  val mockAsyncRequestService = mock[AsyncRequestService]



  def mockSendWithResource(resourcePath: String) = {
    when(mockAsyncRequestService.sendRequest(anyObject[URLBuilder])).thenReturn(
      Future.successful(
        HttpResponse(
          entity = HttpEntity(
            contentType = ContentTypes.`application/json`,
            string      = Source.fromResource(resourcePath).mkString)
        )
      )
    )
  }
  when(mockAsyncRequestService.ec) thenReturn actorSystem.dispatcher
  when(mockAsyncRequestService.materializer) thenReturn materializer
}
