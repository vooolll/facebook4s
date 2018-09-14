package com.github.vooolll.services

import akka.http.scaladsl.{Http, HttpExt}
import akka.http.scaladsl.model._
import com.github.vooolll.services.AsyncRequest.{AsyncResponseContext, UntypedData}
import org.f100ded.scalaurlbuilder.URLBuilder

import scala.concurrent.Future

/**
  * Service that provides async requests to api, via akk http
  */
class AsyncRequest() {

  def apply(url: URLBuilder)(implicit appResources: AppResources): AsyncResponseContext = {
    import appResources._
    implicit val httpExtension = Http()

    new AsyncResponseContext(httpExtension.singleRequest(HttpRequest(uri = url.toString())))
  }

  def post(url: URLBuilder, data: UntypedData)(implicit appResources: AppResources): AsyncResponseContext = {
    import appResources._
    implicit val http = Http()

    val response = http.singleRequest(
      HttpRequest(
        uri    = url.toString(),
        method = HttpMethods.POST,
        entity = FormData(data).toEntity
      )
    )

    new AsyncResponseContext(response)
  }

}

object AsyncRequest {

  type UntypedData = Map[String, String]

  class AsyncResponseContext(val response: Future[HttpResponse])(implicit val httpExtension: HttpExt) {
    def cleanResources() = {
      httpExtension.shutdownAllConnectionPools()
    }
  }

  def apply(): AsyncRequest = new AsyncRequest()
}
