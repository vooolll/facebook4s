package services

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import domain.oauth.{FacebookAppSecret, FacebookClientId, FacebookError, FacebookOauthError}
import io.circe.Decoder
import org.f100ded.scalaurlbuilder.URLBuilder
import serialization.FacebookDecoders.decodeOauthError
import cats.syntax.either._
import services.DomainParseService.{AppResources, FacebookAppResources}

import scala.concurrent.{ExecutionContext, Future}

abstract class FacebookInternals {
  val clientId: FacebookClientId
  val appSecret: FacebookAppSecret

  val domainParseService = DomainParseService()

  val uriService = UriService(clientId, appSecret)

  /**
    * @param message error message
    * @return Future FacebookOauthError
    */
  def facebookError(message: String) = Future.successful(FacebookOauthError(FacebookError(message)).asLeft)


  def sendRequest[A](uri: URLBuilder)(implicit reads: Decoder[A]) = {
    domainParseService.send(uri)(reads, decodeOauthError)(facebookError)(appResources)
  }

  def sendRequestOrFail[A](uri: URLBuilder)(implicit reads: Decoder[A]) = {
    domainParseService.sendOrFail(uri)(reads, decodeOauthError)(facebookError)(appResources)
  }

  def appResources = new FacebookAppResources()
}

trait HasStringValue{
  def value: String
}