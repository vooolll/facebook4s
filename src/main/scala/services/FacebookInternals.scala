package services

import domain.oauth.FacebookError
import io.circe.Decoder
import org.f100ded.scalaurlbuilder.URLBuilder
import serialization.FacebookDecoders.decodeError

import scala.concurrent.Future

abstract class FacebookInternals extends FacebookUrls {

  val domainParing = DomainParsing()
  import domainParing._

  def sendRequest[A](uri: URLBuilder)(implicit reads: Decoder[A]): Future[Either[FacebookError, A]] =
    asDomainResult(uri)(decoders(reads), FacebookAppResources())

  def sendRequestOrFail[A](uri: URLBuilder)(implicit reads: Decoder[A]): Future[A] =
    asDomain(uri)(decoders(reads), FacebookAppResources())

  private[this] def decoders[A](reads: Decoder[A]) = Decoders()(reads, decodeError)
}
