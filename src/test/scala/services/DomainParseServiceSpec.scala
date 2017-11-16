package services

import client.ApplicationResources
import client.FacebookClient.loginErrorFE
import org.scalatest.concurrent.Eventually
import org.scalatest.mockito.MockitoSugar
import org.scalatest.time.{Millis, Span}
import org.scalatest.{AsyncWordSpec, Matchers}
import serialization.FacebookDecoders._
import services.DomainParseService.AppResources

class DomainParseServiceSpec  extends AsyncWordSpec with Matchers with MockitoSugar with Eventually
  with ApplicationResources {

  val domainService = DomainParseService()
  val uriService = UriService()

  implicit val patience = PatienceConfig(timeout = scaled(Span(3000, Millis)), interval = scaled(Span(150, Millis)))

  "DomainParseService" should {
    "terminate actor system after parsing" in {
      val resources = AppResources(system, mat, ec)

      val domainEntity = domainService.send(
        uriService.appTokenUri)(decodeAppAccessToken, decodeOauthError)(loginErrorFE)(resources)
      domainEntity flatMap { _ =>
        resources.system.whenTerminated.map(_ => succeed)
      }
    }
  }
}
