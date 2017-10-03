package services

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import api.DomainTransformer
import domain.{FacebookAppSecret, FacebookClientId}

abstract class FacebookInternals extends ApplicationResources {
  val clientId: FacebookClientId
  val appSecret: FacebookAppSecret

  val asyncRequestService = AsyncRequestService()(system, mat, ec)

  val uriService = UriService(clientId, appSecret)
  val transformer = new DomainTransformer()(mat, ec)
}

trait ApplicationResources {
  implicit lazy val system = ActorSystem()
  implicit lazy val mat = ActorMaterializer()
  implicit lazy val ec = system.dispatcher
}
