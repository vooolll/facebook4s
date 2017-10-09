package services

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import domain.{FacebookAppSecret, FacebookClientId}

abstract class FacebookInternals extends ApplicationResources {
  val clientId: FacebookClientId
  val appSecret: FacebookAppSecret

  val domainParseService = DomainParseService(system, mat, ec)

  val uriService = UriService(clientId, appSecret)
}

trait ApplicationResources {
  implicit lazy val system = ActorSystem()
  implicit lazy val mat = ActorMaterializer()
  implicit lazy val ec = system.dispatcher
}
