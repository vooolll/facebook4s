package client

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.stream.ActorMaterializer
import config.FacebookConfig.{appSecret, clientId}
import org.f100ded.scalaurlbuilder.URLBuilder
import org.mockito.Matchers.anyObject
import org.mockito.{Matchers => M}
import org.mockito.Mockito._
import org.scalatest._
import org.scalatest.mockito.MockitoSugar
import services.DomainParseService.AppResources
import services.{AsyncRequestService, DomainParseService, FacebookInternals}

import scala.concurrent.Future
import scala.io.Source


trait FacebookClientStubSupport extends fixture.AsyncWordSpec with Matchers {

  type FixtureParam = ClientProbe with FacebookClient

  def withFixture(test: OneArgAsyncTest): FutureOutcome = {
    withFixture(test.toNoArgAsyncTest(ClientProbe()))
  }

}

trait FacebookClientSupport extends fixture.AsyncWordSpec with Matchers {

  type FixtureParam = FacebookClient

  def withFixture(test: OneArgAsyncTest): FutureOutcome = {
    withFixture(test.toNoArgAsyncTest(FacebookClient()))
  }

}

trait ClientProbe extends FacebookInternals with MockitoSugar {
  import ClientProbe._
  val facebookServices = mock[FacebookInternals]

  val mockAsyncRequestService = mock[AsyncRequestService]

  implicit lazy val system = ActorSystem()
  implicit lazy val mat = ActorMaterializer()
  implicit lazy val ec = system.dispatcher

  def mockSendWithResource(resourcePath: String) = {
    when(mockAsyncRequestService.sendRequest(anyObject[URLBuilder])(
      anyObject[AppResources])).thenReturn(
      Future.successful(
        HttpResponse(
          entity = HttpEntity(
            contentType = ContentTypes.`application/json`,
            string      = readFile(resourcePath))
        )
      )
    )
  }

  def mockSendError(resourcePath: String) = {
    when(mockAsyncRequestService.sendRequest(anyObject[URLBuilder])(
      anyObject[AppResources])).thenReturn(
      Future.successful(
        HttpResponse(
          status = StatusCodes.BadRequest,
          entity = HttpEntity(
            contentType = ContentTypes.`application/json`,
            string      = readFile(resourcePath))
        )
      )
    )
  }

  override val domainParseService = new DomainParseService(mockAsyncRequestService)
}

object ClientProbe {
  def apply() = new FacebookClient(clientId, appSecret) with ClientProbe

  def readFile(resourcePath: String) = Source.fromResource(resourcePath).mkString
}