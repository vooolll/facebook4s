package com.github.vooolll.services

import com.github.vooolll.domain.oauth.FacebookError
import io.circe.Decoder
import org.f100ded.scalaurlbuilder.URLBuilder
import com.github.vooolll.serialization.FacebookDecoders.decodeError
import com.github.vooolll.services.DomainParsing.DomainParsingContext

import scala.concurrent.Future

abstract class FacebookInternals extends FacebookUrls {

  val domainParing = DomainParsing()
  import domainParing._

  def sendRequest[A](uri: URLBuilder)(implicit decoder: Decoder[A]): Future[Either[FacebookError, A]] =
    asDomainResult(uri)(DomainParsingContext(decoder))

  def sendRequestOrFail[A](uri: URLBuilder)(implicit decoder: Decoder[A]): Future[A] =
    asDomain(uri)(DomainParsingContext(decoder))

}
