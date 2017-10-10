package services

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import domain.{FacebookAppSecret, FacebookClientId}
import services.DomainParseService.AppResources

abstract class FacebookInternals {
  val clientId: FacebookClientId
  val appSecret: FacebookAppSecret

  val domainParseService = DomainParseService()

  val uriService = UriService(clientId, appSecret)

  def appResources = {
    implicit val system = ActorSystem()
    implicit val mat = ActorMaterializer()

    implicit val ec = system.dispatcher
    AppResources(system, mat, ec)
  }
}