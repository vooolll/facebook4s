package services

import io.circe.Decoder
import org.f100ded.scalaurlbuilder.URLBuilder
import serialization.FacebookDecoders.decodeOauthError

abstract class FacebookInternals extends FacebookUrls {

  val domainParing = DomainParsing()
  import domainParing._

  def sendRequest[A](uri: URLBuilder)(implicit reads: Decoder[A]) = {
    httpResponseToDomainResult(uri)(decoders(reads), FacebookAppResources())
  }

  def sendRequestOrFail[A](uri: URLBuilder)(implicit reads: Decoder[A]) = {
    httpResponseToDomain(uri)(decoders(reads), FacebookAppResources())
  }

  private[this] def decoders[A](reads: Decoder[A]) = Decoders()(reads, decodeOauthError)
}
