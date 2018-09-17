package com.github.vooolll.services

import com.github.vooolll.base.{AsyncResourceSpec, TestUrls}
import com.github.vooolll.serialization.FacebookDecoders._
import com.github.vooolll.services.DomainParsing.DomainParsingContext

class DomainParsingSpec extends AsyncResourceSpec {

  val domainService = DomainParsing()
  "DomainParseService" should {
    "terminate actor system after parsing" in {
      val domainEntity = domainService.asDomainResult(TestUrls.appTokenUri)(DomainParsingContext()(decodeAppAccessToken, appResources))
      domainEntity flatMap { _ =>
        appResources.actorSystem.whenTerminated.map(_ => succeed)
      }
    }
  }
}
