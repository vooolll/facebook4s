package services

import api.DomainTransformer
import domain.{FacebookAppSecret, FacebookClientId}

abstract class FacebookInternalServices {
  val clientId: FacebookClientId
  val appSecret: FacebookAppSecret

  val asyncRequestService = AsyncRequestService()
  import asyncRequestService._

  val uriService = URIService(clientId, appSecret)
  val transformer = new DomainTransformer()
}
