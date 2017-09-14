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

class FacebookClient() extends PlayJsonSupport {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  def appAccessToken(): Future[AccessToken] = {
    val response: Future[HttpResponse] =
      Http().singleRequest(HttpRequest(
        uri = s"$host${version.show}$oauthUri?client_id=$clientId&client_secret=$appSecret" +
          s"&grant_type=client_credentials"))

    response.flatMap(response => Unmarshal(response.entity).to[AccessToken])
  }

}

