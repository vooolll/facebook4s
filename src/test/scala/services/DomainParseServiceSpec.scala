package services

import api.ApplicationResources
import api.FacebookClient.loginErrorFE
import api.FacebookJsonSerializers._
import org.scalatest.concurrent.Eventually
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncWordSpec, Matchers}
import org.scalatest.time.{Millis, Span}
import services.DomainParseService.AppResources

import scala.concurrent.Future

class DomainParseServiceSpec  extends AsyncWordSpec with Matchers with MockitoSugar with Eventually
  with ApplicationResources {

  val domainService = DomainParseService()
  val uriService = UriService()

  implicit val patience = PatienceConfig(timeout = scaled(Span(3000, Millis)), interval = scaled(Span(150, Millis)))

  "DomainParseService" should {
    "terminate actor system after parsing" in {
      val resources = AppResources(system, mat, ec)

      val domainEntity = domainService.send(
        uriService.appTokenUri)(facebookAppAccessTokenReads, facebookLoginErrorReads)(loginErrorFE)(resources)
      domainEntity flatMap { _ =>
        resources.system.whenTerminated.map(_ => succeed)
      }
    }
  }
}
