package services

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import client.FacebookClient.loginErrorFE
import domain.oauth.{FacebookAppSecret, FacebookClientId}
import io.circe.Decoder
import org.f100ded.scalaurlbuilder.URLBuilder
import serialization.FacebookDecoders.decodeOauthError
import services.DomainParseService.AppResources

abstract class FacebookInternals {
  val clientId: FacebookClientId
  val appSecret: FacebookAppSecret

  val domainParseService = DomainParseService()

  val uriService = UriService(clientId, appSecret)

  def sendRequest[A](uri: URLBuilder)(reads: Decoder[A]) = {
    domainParseService.send(uri)(reads, decodeOauthError)(loginErrorFE)(appResources)
  }

  def sendRequestOrFail[A](uri: URLBuilder)(reads: Decoder[A]) = {
    domainParseService.sendOrFail(uri)(reads, decodeOauthError)(loginErrorFE)(appResources)
  }

  def appResources = {
    implicit val system = ActorSystem()
    implicit val mat = ActorMaterializer()

    implicit val ec = system.dispatcher
    AppResources(system, mat, ec)
  }
}