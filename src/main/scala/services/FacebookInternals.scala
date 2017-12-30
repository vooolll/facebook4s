package services

import domain.oauth.{FacebookAppSecret, FacebookClientId}
import io.circe.Decoder
import org.f100ded.scalaurlbuilder.URLBuilder
import serialization.FacebookDecoders.decodeOauthError

abstract class FacebookInternals {
  val clientId: FacebookClientId
  val appSecret: FacebookAppSecret

  val domainParing = new DomainParsing(new AsyncRequestService())

  val uriService = UriService(clientId, appSecret)

  def sendRequest[A](uri: URLBuilder)(implicit reads: Decoder[A]) = {
    domainParing.httpResponseToDomainResult(uri)(Decoders()(reads, decodeOauthError), appResources)
  }

  def sendRequestOrFail[A](uri: URLBuilder)(implicit reads: Decoder[A]) = {
    domainParing.httpResponseToDomain(uri)(reads, appResources)
  }

  def appResources = new FacebookAppResources()
}

trait HasStringValue{
  def value: String
}