package com.github.vooolll.services

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.unmarshalling.Unmarshal
import cats.implicits._
import com.github.vooolll.domain.oauth.FacebookError
import com.github.vooolll.services.AsyncRequest.{AsyncResponseContext, UntypedData}
import com.github.vooolll.services.DomainParsing.{DomainParsingContext, GetRequestContext, PostRequestContext}
import com.typesafe.scalalogging.LazyLogging
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.Decoder
import org.f100ded.scalaurlbuilder.URLBuilder

import scala.concurrent.Future

class DomainParsing(asyncRequest: AsyncRequest) extends FailFastCirceSupport with LazyLogging {

  import com.github.vooolll.serialization.FacebookDecoders.decodeError

  def asDomain[A](url: URLBuilder)
  (implicit domainContext: DomainParsingContext[A]): Future[A] = {
    import domainContext.appResources.executionContext
    domainByUrl(url) map valueOrException
  }

  def asDomainResult[A](url: URLBuilder)
  (implicit domainContext: DomainParsingContext[A]): Future[Either[FacebookError, A]] = {
    domainByUrl(url)
  }

  private[this] def domainByUrl[A](url: URLBuilder)
  (implicit domainContext: DomainParsingContext[A]): Future[Either[FacebookError, A]] = {
    import domainContext._
    requestContext match {
      case GetRequestContext           => responseAsDomainResult(asyncRequest(url))
      case context: PostRequestContext => responseAsDomainResult(asyncRequest.post(url, context.params))
    }
  }

  private[this] def responseAsDomainResult[A](responseContext: AsyncResponseContext)
  (implicit domainContext: DomainParsingContext[A]): Future[Either[FacebookError, A]] = {
    import domainContext._
    import domainContext.appResources._
    for {
      httpResponse <- responseContext.response
      domain       <- parseResult(responseEntityResult(httpResponse))(entityDecoder, appResources)
      _            <- responseContext.cleanResources()
      _            <- actorSystem.terminate()
    } yield domain
  }

  private[this] def valueOrException[A](result: Either[FacebookError, A]): A = result match {
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

  private[this] def parseResult[A](httpEntityResult: Either[HttpEntity, HttpEntity])
    (implicit decoder: Decoder[A], appResources: AppResources): Future[Either[FacebookError, A]] = {
    import appResources._

    httpEntityResult match {
      case Right(httpEntity) => parse(httpEntity)(decoder, appResources) map(a => a.asRight)
      case Left(httpEntity)  => parse(httpEntity)(decodeError, appResources) map(b => b.asLeft)
    }
  }
}

object DomainParsing {
  def apply(asyncRequest: AsyncRequest): DomainParsing = new DomainParsing(asyncRequest)
  def apply(): DomainParsing = new DomainParsing(AsyncRequest())

  trait RequestContext

  case object GetRequestContext extends RequestContext
  case class PostRequestContext(params: UntypedData) extends RequestContext

  class DomainParsingContext[A](val requestContext: RequestContext)(
    implicit val entityDecoder: Decoder[A],
    implicit val appResources: AppResources)

  object DomainParsingContext {
    def apply[A](entityDecoder: Decoder[A]): DomainParsingContext[A] = {
      new DomainParsingContext(GetRequestContext)(entityDecoder, FacebookAppResources())
    }
  }
}