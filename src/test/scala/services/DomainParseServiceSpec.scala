package services

import api.FacebookClient.loginErrorFE
import api.FacebookJsonSerializers._
import org.scalatest.concurrent.Eventually
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncWordSpec, Matchers}
import org.scalatest.time.{Millis, Span}

import scala.concurrent.Future

class DomainParseServiceSpec  extends AsyncWordSpec with Matchers with MockitoSugar with Eventually
  with ApplicationResources {

  val domainService = DomainParseService(system, mat, ec)
  val uriService = UriService()

  implicit val patience = PatienceConfig(timeout = scaled(Span(2000, Millis)))

  "DomainParseService" should {
    "terminate actor system after parsing" in {
      val domainEntity = domainService.sendAndParseTo(
        uriService.appTokenUri)(facebookAppAccessTokenReads, facebookLoginErrorReads)(loginErrorFE)

      domainEntity flatMap { _ =>
        val termination = system.whenTerminated
        eventually {
          Future.successful(termination.isCompleted shouldBe true)
        }
      }
    }
  }
}
