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
import play.api.libs.json.{JsError, JsSuccess, Json}

import scala.concurrent.Future
import FacebookJsonSerializers._
import akka.http.scaladsl.unmarshalling.Unmarshal

class FacebookClient() {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  def appAccessToken(): Future[AccessToken] = {

    val responseFuture: Future[HttpResponse] =
      Http().singleRequest(HttpRequest(
        uri = s"$host${version.show}$oauthUri?client_id=$clientId&client_secret=$appSecret" +
          s"&grant_type=client_credentials"))

    responseFuture.flatMap { response =>
      Unmarshal(response.entity).to[String] map { s =>
        Json.parse(s).validate[AccessToken] match {
          case s: JsSuccess[AccessToken] => s.get
          case e: JsError => throw new RuntimeException(e.toString)
        }
      }
    }
  }

}

