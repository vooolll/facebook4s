package com.github.vooolll.base

import com.github.vooolll.services._

import scala.concurrent.ExecutionContext

trait AsyncResourceSpec extends AsyncSpec {
  implicit val appResources: FacebookAppResources = FacebookAppResources()
  implicit val ec: ExecutionContext = executionContext
}

