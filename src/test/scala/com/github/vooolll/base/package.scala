package com.github.vooolll

import java.time.Instant

import cats.implicits._
import com.github.vooolll.config.FacebookConstants.dateFormat
import com.github.vooolll.domain.oauth._

import scala.concurrent.duration._

package object base {

  val userAccessToken = FacebookAccessToken(
    TokenValue("token"),
    UserAccessToken("bearer", 5107587.seconds)
  )

  val clientCode = FacebookClientCode("test-test-test-test", "machine id".some)

  def toInstant(string: String) = dateFormat.parse(string, Instant.from(_))

}
