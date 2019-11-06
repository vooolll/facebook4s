package com.github.vooolll.services

import com.github.vooolll.base.{AsyncResourceSpec, TestUrls}
import com.github.vooolll.serialization.FacebookDecoders._
import com.github.vooolll.services.DomainParsing.DomainParsingContext

class DomainParsingSpec extends AsyncResourceSpec {

  val domainService = DomainParsing()
  "DomainParseService" should {
    "terminate actor system after parsing" in {
      val domainParsingContext = DomainParsingContext(decodeAppAccessToken)
      val domainEntity =
        domainService.asDomainResult(TestUrls.appTokenUri)(domainParsingContext)
      domainEntity flatMap { _ =>
        domainParsingContext.appResources.actorSystem.whenTerminated
          .map(_ => succeed)
      }
    }
  }
}
