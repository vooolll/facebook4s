package services

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import org.f100ded.scalaurlbuilder.URLBuilder

import scala.concurrent.Future

/**
  * Service that provides async requests to api, via akk http
  */
class AsyncRequestService() {
  def sendRequest(url: URLBuilder)(implicit appResources: AppResources): Future[HttpResponse] = {
    import appResources._
    Http().singleRequest(HttpRequest(uri = url.toString()))(materializer)
  }
}

object AsyncRequestService {
  def apply(): AsyncRequestService = new AsyncRequestService()
}
