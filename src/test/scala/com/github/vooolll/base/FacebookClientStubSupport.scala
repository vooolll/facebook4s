package com.github.vooolll.base

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.stream.ActorMaterializer
import org.f100ded.scalaurlbuilder.URLBuilder
import org.mockito.Matchers.anyObject
import org.mockito.Mockito._
import org.scalatest._
import org.scalatest.mockito.MockitoSugar

import com.github.vooolll.services._
import com.github.vooolll.client.FacebookClient
import com.github.vooolll.config.FacebookConfig.{appSecret, clientId}

import scala.concurrent.Future
import scala.io.Source


trait FacebookClientStubSupport extends fixture.AsyncWordSpec with Matchers {

  type FixtureParam = ClientProbe with FacebookClient

  def withFixture(test: OneArgAsyncTest): FutureOutcome = {
    withFixture(test.toNoArgAsyncTest(ClientProbe()))
  }

}

trait ClientProbe extends FacebookInternals with MockitoSugar {
  import ClientProbe._
  val facebookServices = mock[FacebookInternals]

  val mockAsyncRequestService = mock[AsyncRequest]

  implicit lazy val system = ActorSystem()

  def mockSendWithResource(resourcePath: String) = {
    when(mockAsyncRequestService.apply(anyObject[URLBuilder])(
      anyObject[AppResources])).thenReturn(
        (
          Http(),
          Future.successful(
            HttpResponse(
              entity = HttpEntity(
                contentType = ContentTypes.`application/json`,
                string      = readFile(resourcePath))
            )
          )
        )
    )
  }

  def mockSendError(resourcePath: String) = {
    when(mockAsyncRequestService.apply(anyObject[URLBuilder])(
      anyObject[AppResources])).thenReturn(
      (
        Http(),
        Future.successful(
          HttpResponse(
            status = StatusCodes.BadRequest,
            entity = HttpEntity(
              contentType = ContentTypes.`application/json`,
              string      = readFile(resourcePath))
          )
        )
      )
    )
  }

  override val domainParing = DomainParsing(mockAsyncRequestService)
}

object ClientProbe {
  def apply() = new FacebookClient(clientId, appSecret) with ClientProbe

  def readFile(resourcePath: String) = Source.fromResource(resourcePath).mkString
}