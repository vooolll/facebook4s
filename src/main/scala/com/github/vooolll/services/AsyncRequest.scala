package com.github.vooolll.services

import akka.http.scaladsl.{Http, HttpExt}
import akka.http.scaladsl.model._
import com.github.vooolll.services.AsyncRequest.UntypedData
import org.f100ded.scalaurlbuilder.URLBuilder

import scala.concurrent.Future

/**
  * Service that provides async requests to api, via akk http
  */
class AsyncRequest() {

  def apply(url: URLBuilder)(implicit appResources: AppResources): (HttpExt, Future[HttpResponse]) = {
    import appResources._
    val http = Http()

    (http, http.singleRequest(HttpRequest(uri = url.toString())))
  }

  def post(url: URLBuilder, data: UntypedData)(implicit appResources: AppResources):(HttpExt, Future[HttpResponse]) = {
    import appResources._
    val http = Http()

    val response = http.singleRequest(
      HttpRequest(
        uri    = url.toString(),
        method = HttpMethods.POST,
        entity = FormData(data).toEntity
      )
    )

    (http, response)
  }

}

object AsyncRequest {

  type UntypedData = Map[String, String]

  def apply(): AsyncRequest = new AsyncRequest()
}
