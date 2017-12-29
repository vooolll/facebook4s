package services

import cats.syntax.either._
import domain.oauth.{FacebookError, FacebookOauthError}
import org.scalatest.concurrent.Eventually
import org.scalatest.mockito.MockitoSugar
import org.scalatest.time.{Millis, Span}
import org.scalatest.{AsyncWordSpec, Matchers}
import serialization.FacebookDecoders._
import services.DomainParseService.{AppResources, FacebookAppResources}

import scala.concurrent.Future

class DomainParseServiceSpec  extends AsyncWordSpec with Matchers with MockitoSugar with Eventually {

  val domainService = DomainParseService()
  val uriService = UriService()

  implicit val patience = PatienceConfig(timeout = scaled(Span(3000, Millis)), interval = scaled(Span(150, Millis)))

  "DomainParseService" should {
    "terminate actor system after parsing" in {
      val resources: AppResources = new FacebookAppResources()

      def error(string: String) = Future.successful(FacebookOauthError(FacebookError(string)).asLeft)

      val domainEntity = domainService.send(uriService.appTokenUri)(
        decodeAppAccessToken, decodeOauthError)(error)(resources)
      domainEntity flatMap { _ =>
        resources.actorSystem.whenTerminated.map(_ => succeed)
      }
    }
  }
}
