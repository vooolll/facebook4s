package services

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer
import org.f100ded.scalaurlbuilder.URLBuilder

import scala.concurrent.Future

class AsyncRequestService {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  def sendRequest(url: URLBuilder): Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = url.toString()))
}

object AsyncRequestService {
  def apply() = new AsyncRequestService()
}
