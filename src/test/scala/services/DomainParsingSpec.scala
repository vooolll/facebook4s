package services

import base.TestUrls
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncWordSpec, Matchers}
import serialization.FacebookDecoders._

class DomainParsingSpec  extends AsyncWordSpec with Matchers with MockitoSugar {

  val domainService = DomainParsing()
  "DomainParseService" should {
    "terminate actor system after parsing" in {
      val resources: AppResources = FacebookAppResources()

      val domainEntity = domainService.httpResponseToDomainResult(TestUrls.appTokenUri)(
        Decoders()(decodeAppAccessToken, decodeOauthError), resources)
      domainEntity flatMap { _ =>
        resources.actorSystem.whenTerminated.map(_ => succeed)
      }
    }
  }
}
