package services

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncWordSpec, Matchers}
import serialization.FacebookDecoders._

class DomainParsingSpec  extends AsyncWordSpec with Matchers with MockitoSugar {

  val domainService = new DomainParsing(new AsyncRequestService())
  val uriService = UriService()

  "DomainParseService" should {
    "terminate actor system after parsing" in {
      val resources: AppResources = new FacebookAppResources()

      val domainEntity = domainService.httpResponseToDomainResult(uriService.appTokenUri)(
        Decoders()(decodeAppAccessToken, decodeOauthError), resources)
      domainEntity flatMap { _ =>
        resources.actorSystem.whenTerminated.map(_ => succeed)
      }
    }
  }
}
