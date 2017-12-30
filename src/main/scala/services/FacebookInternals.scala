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
    domainParing.httpResponseToDomainResult(uri)(decoders(reads), appResources)
  }

  def sendRequestOrFail[A](uri: URLBuilder)(implicit reads: Decoder[A]) = {
    domainParing.httpResponseToDomain(uri)(decoders(reads), appResources)
  }

  private def decoders[A](reads: Decoder[A]) = Decoders()(reads, decodeOauthError)

  def appResources = new FacebookAppResources()
}

trait HasStringValue{
  def value: String
}