package services

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer
import org.f100ded.scalaurlbuilder.URLBuilder

import scala.concurrent.{ExecutionContext, Future}

class AsyncRequestService()(implicit system: ActorSystem, mat: ActorMaterializer, ec: ExecutionContext) {
  def sendRequest(url: URLBuilder): Future[HttpResponse] = Http().singleRequest(HttpRequest(uri = url.toString()))
}

object AsyncRequestService {
  def apply()(implicit system: ActorSystem, mat: ActorMaterializer, ec: ExecutionContext) =
    new AsyncRequestService()(system, mat, ec)
}
