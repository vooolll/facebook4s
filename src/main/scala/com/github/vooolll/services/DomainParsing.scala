package com.github.vooolll.services

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.unmarshalling.Unmarshal
import cats.implicits._
import com.typesafe.scalalogging.LazyLogging
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import com.github.vooolll.domain.oauth.FacebookError
import com.github.vooolll.services.AsyncRequest.{AsyncResponseContext, UntypedData}
import com.github.vooolll.services.DomainParsing.{GetRequestContext, PostRequestContext, RequestContext}
import io.circe.Decoder
import org.f100ded.scalaurlbuilder.URLBuilder

import scala.concurrent.Future

class DomainParsing(asyncRequest: AsyncRequest) extends FailFastCirceSupport with LazyLogging {

  def asDomain[A]
  (url: URLBuilder, requestContext: RequestContext = GetRequestContext)
  (implicit entityDecoder: Decoders[A, FacebookError], appResources: AppResources): Future[A] = {
    import appResources.executionContext
    domainByUrl(url, requestContext) map valueOrException
  }

  def asDomainResult[A]
  (url: URLBuilder, requestContext: RequestContext = GetRequestContext)
  (implicit entityDecoder: Decoders[A, FacebookError], appResources: AppResources): Future[Either[FacebookError, A]] = {
    domainByUrl(url, requestContext)
  }

  private def domainByUrl[A]
  (url: URLBuilder, requestContext: RequestContext = GetRequestContext)
  (implicit entityDecoder: Decoders[A, FacebookError], appResources: AppResources): Future[Either[FacebookError, A]] = {
    requestContext match {
      case GetRequestContext           => responseAsDomainResult(asyncRequest(url))
      case context: PostRequestContext => responseAsDomainResult(asyncRequest.post(url, context.params))
    }
  }

  private[this] def responseAsDomainResult[A](responseContext: AsyncResponseContext)
    (implicit entityDecoder: Decoders[A, FacebookError], appResources: AppResources): Future[Either[FacebookError, A]] = {
    import appResources._
    for {
      httpResponse <- responseContext.response
      domain       <- parseResult(responseEntityResult(httpResponse))
      _            <- responseContext.cleanResources()
      _            <- actorSystem.terminate()
    } yield domain
  }

  private[this] def valueOrException[A, B <: FacebookError](result: Either[B, A]): A = result match {
    case Right(value) => value
    case Left(error)  => throw new RuntimeException(error.message)
  }

  private[this] def responseEntityResult(response: HttpResponse): Either[HttpEntity, HttpEntity] = {
    response.status match {
      case StatusCodes.OK                  => response.entity.asRight
      case StatusCodes.BadRequest          => response.entity.asLeft
      case StatusCodes.InternalServerError =>
        logger.warn(s"internal server error $response")
        response.entity.asLeft
      case _                               =>
        logger.warn("unknown status code")
        response.entity.asLeft
    }
  }

  private[this] def parse[A](httpEntity: HttpEntity)
    (implicit decoder: Decoder[A], appResources: AppResources): Future[A] = {
    import appResources._
    Unmarshal[HttpEntity](httpEntity.withContentType(ContentTypes.`application/json`)).to[A].recover {
      case e => throw new RuntimeException(e.getLocalizedMessage)
    }
  }

  private[this] def parseResult[A, B <: FacebookError](httpEntityResult: Either[HttpEntity, HttpEntity])
    (implicit decoders: Decoders[A, FacebookError], appResources: AppResources): Future[Either[FacebookError, A]] = {
    import appResources._

    httpEntityResult match {
      case Right(httpEntity) => parse(httpEntity)(decoders.success, appResources) map(a => a.asRight)
      case Left(httpEntity)  => parse(httpEntity)(decoders.failure, appResources) map(b => b.asLeft)
    }
  }
}

object DomainParsing {
  def apply(asyncRequest: AsyncRequest): DomainParsing = new DomainParsing(asyncRequest)
  def apply(): DomainParsing = new DomainParsing(AsyncRequest())

  trait RequestContext

  case object GetRequestContext extends RequestContext
  case class PostRequestContext(params: UntypedData) extends RequestContext
}

class Decoders[A, B](val success: Decoder[A], val failure: Decoder[FacebookError])