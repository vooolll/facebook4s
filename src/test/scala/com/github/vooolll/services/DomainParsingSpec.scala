package com.github.vooolll.services

import com.github.vooolll.base.TestUrls
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncWordSpec, Matchers}
import com.github.vooolll.serialization.FacebookDecoders._

class DomainParsingSpec  extends AsyncWordSpec with Matchers with MockitoSugar {

  val domainService = DomainParsing()
  "DomainParseService" should {
    "terminate actor system after parsing" in {
      val resources: AppResources = FacebookAppResources()

      val domainEntity = domainService.asDomainResult(TestUrls.appTokenUri)(
        Decoders()(decodeAppAccessToken, decodeError), resources)
      domainEntity flatMap { _ =>
        resources.actorSystem.whenTerminated.map(_ => succeed)
      }
    }
  }
}
