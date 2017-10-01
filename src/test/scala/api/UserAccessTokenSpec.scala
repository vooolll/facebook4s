package api

import java.time.Instant

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse}
import akka.stream.ActorMaterializer
import config.FacebookConfig._
import domain.UserAccessToken
import org.f100ded.scalaurlbuilder.URLBuilder
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncWordSpec, Matchers}
import services.{AsyncRequestService, FacebookInternals}

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt
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
          token.tokenValue.value shouldBe "test token"
          token.tokenType shouldBe UserAccessToken("bearer", 5107587.seconds)
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
