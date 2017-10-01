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
import services.{AsyncRequestService, FacebookInternals}
import config.FacebookConfig._

import scala.concurrent.Future
import scala.io.Source

class UserAccessTokenSpec
  extends AsyncWordSpec with Matchers with MockitoSugar with MockedAsyncRequestService {

  trait ClientProbe extends FacebookInternals {
    override implicit lazy val system = ActorSystem()
    override implicit lazy val mat = ActorMaterializer()
    override implicit lazy val ec = system.dispatcher
    override val asyncRequestService = mockAsyncRequestService
    override val transformer = new DomainTransformer()
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

  val facebookServices = mock[FacebookInternals]
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
}
