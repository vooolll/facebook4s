package com.github.vooolll.services

import com.github.vooolll.base.{AsyncResourceSpec, TestUrls}
import com.github.vooolll.serialization.FacebookDecoders._

class DomainParsingSpec extends AsyncResourceSpec {

  val domainService = DomainParsing()
  "DomainParseService" should {
    "terminate actor system after parsing" in {

      val domainEntity = domainService.asDomainResult(TestUrls.appTokenUri)(
        new Decoders(decodeAppAccessToken, decodeError), appResources)
      domainEntity flatMap { _ =>
        appResources.actorSystem.whenTerminated.map(_ => succeed)
      }
    }
  }
}
