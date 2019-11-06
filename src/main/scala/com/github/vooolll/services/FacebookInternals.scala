package com.github.vooolll.services

import com.github.vooolll.domain.oauth.FacebookError
import io.circe.Decoder
import org.f100ded.scalaurlbuilder.URLBuilder
import com.github.vooolll.serialization.FacebookDecoders.decodeError
import com.github.vooolll.services.DomainParsing.{DomainParsingContext, GetRequestContext, RequestContext}

import scala.concurrent.Future

abstract class FacebookInternals extends FacebookUrls {

  val domainParing = DomainParsing()
  import domainParing._

  def sendRequest[A](
    uri: URLBuilder,
    context: RequestContext = GetRequestContext
  )(implicit decoder: Decoder[A]): Future[Either[FacebookError, A]] =
    asDomainResult(uri)(DomainParsingContext(decoder, context))

  def sendRequestOrFail[A](
    uri: URLBuilder,
    context: RequestContext = GetRequestContext
  )(implicit decoder: Decoder[A]): Future[A] =
    asDomain(uri)(DomainParsingContext(decoder, context))

}
