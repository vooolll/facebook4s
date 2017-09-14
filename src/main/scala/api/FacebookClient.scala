package api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer
import cats.implicits._
import config.FacebookConfig.{appSecret, clientId, version}
import config.FacebookConstants._
import domain.AccessToken
import domain.FacebookVersionInstances._

import scala.concurrent.Future
import FacebookJsonSerializers._
import akka.http.scaladsl.unmarshalling.Unmarshal
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import org.f100ded.scalaurlbuilder.URLBuilder

class FacebookClient() extends PlayJsonSupport {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  def appAccessToken(): Future[AccessToken] = {
    val url = URLBuilder(base = host)
      .withPathSegments(version.show, oauthUri)
      .withQueryParameters(
        "client_id"     -> clientId,
        "client_secret" -> appSecret,
        "grant_type"    -> "client_credentials"
      )

    for {
      response <- Http().singleRequest(HttpRequest(uri = url.toString()))
      accessToken <- Unmarshal(response.entity).to[AccessToken]
    } yield accessToken
  }

}

