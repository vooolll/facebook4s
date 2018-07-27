package com.github.vooolll.services

import akka.http.scaladsl.{Http, HttpExt}

import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
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
}

object AsyncRequest {
  def apply(): AsyncRequest = new AsyncRequest()
}
