package api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import api.FacebookJsonSerializers._
import cats.implicits._
import config.FacebookConfig.{appSecret, clientId, version}
import config.FacebookConstants._
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import domain.FacebookAccessToken
import domain.FacebookVersionInstances._
import org.f100ded.scalaurlbuilder.URLBuilder
import domain.FacebookClientIdOps._
import domain.FacebookAppSecretOps._

import scala.concurrent.Future

class FacebookClient() extends PlayJsonSupport {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  def appAccessToken(): Future[FacebookAccessToken] = {
    val url = URLBuilder(base = host)
      .withPathSegments(version.show, oauthUri)
      .withQueryParameters(
        "client_id"     -> clientId.show,
        "client_secret" -> appSecret.show,
        "grant_type"    -> "client_credentials"
      )

    for {
      response <- Http().singleRequest(HttpRequest(uri = url.toString()))
      accessToken <- Unmarshal(response.entity).to[FacebookAccessToken]
    } yield accessToken
  }

}

