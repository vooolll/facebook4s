package services

import domain.oauth.{FacebookAppSecret, FacebookClientId}
import io.circe.Decoder
import org.f100ded.scalaurlbuilder.URLBuilder
import serialization.FacebookDecoders.decodeOauthError

abstract class FacebookInternals {

  val clientId: FacebookClientId

  val appSecret: FacebookAppSecret

  val domainParing = DomainParsing()

  val uriService = UriService(clientId, appSecret)

  def sendRequest[A](uri: URLBuilder)(implicit reads: Decoder[A]) = {
    domainParing.httpResponseToDomainResult(uri)(decoders(reads), FacebookAppResources())
  }

  def sendRequestOrFail[A](uri: URLBuilder)(implicit reads: Decoder[A]) = {
    domainParing.httpResponseToDomain(uri)(decoders(reads), FacebookAppResources())
  }

  private def decoders[A](reads: Decoder[A]) = Decoders()(reads, decodeOauthError)
}

trait HasStringValue{
  def value: String
}