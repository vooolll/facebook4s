package services

import cats.syntax.either._
import domain.oauth.{FacebookAppSecret, FacebookClientId, FacebookError, FacebookOauthError}
import io.circe.Decoder
import org.f100ded.scalaurlbuilder.URLBuilder
import serialization.FacebookDecoders.decodeOauthError
import services.DomainParseService.FacebookAppResources

import scala.concurrent.Future

abstract class FacebookInternals {
  val clientId: FacebookClientId
  val appSecret: FacebookAppSecret

  val domainParseService = DomainParseService()

  val uriService = UriService(clientId, appSecret)

  import domainParseService._

  /**
    * @param message error message
    * @return Future FacebookOauthError
    */
  def facebookError(message: String) = Future.successful(FacebookOauthError(FacebookError(message)).asLeft)


  def sendRequest[A](uri: URLBuilder)(implicit reads: Decoder[A]) = {
    send(uri)(reads, decodeOauthError)(facebookError)(appResources)
  }

  def sendRequestOrFail[A](uri: URLBuilder)(implicit reads: Decoder[A]) = {
    sendOrFail(uri)(reads, decodeOauthError)(facebookError)(appResources)
  }

  def appResources = new FacebookAppResources()
}

trait HasStringValue{
  def value: String
}