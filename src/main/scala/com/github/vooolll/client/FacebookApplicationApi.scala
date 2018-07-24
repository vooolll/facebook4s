package com.github.vooolll.client

import com.github.vooolll.client.FacebookClient._
import com.github.vooolll.services.FacebookInternals

import scala.concurrent.Future

trait FacebookApplicationApi extends FacebookInternals {

  import com.github.vooolll.serialization.FacebookDecoders.decodeApplication

  /**
    * @param applicationId Facebook application(client) id
    * @param accessToken Facebook user access token
    * @return Facebook application details
    *         @throws scala.RuntimeException if facebook responds with bad request
    */
  def application(applicationId: ApplicationId)(implicit accessToken: AccessToken): Future[Application] =
    sendRequestOrFail(applicationUri(accessToken, applicationId))

  /**
    * @param applicationId Facebook application(client) id
    * @param accessToken Facebook user access token
    * @return Either facebook application details or error FacebookError
    */
  def applicationResult(applicationId: ApplicationId)(implicit accessToken: AccessToken): FutureResult[Application] =
    sendRequest(applicationUri(accessToken, applicationId))
    
}
