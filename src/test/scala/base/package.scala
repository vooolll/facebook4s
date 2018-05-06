import java.time.Instant

import cats.implicits._
import config.FacebookConstants.dateFormat
import domain.oauth._

import scala.concurrent.duration._

package object base {

  val userAccessToken = FacebookAccessToken(
    TokenValue("token"), UserAccessToken("bearer", 5107587.seconds))

  val clientCode = FacebookClientCode("test-test-test-test", "machine id".some)

  def toInstant(string: String) = dateFormat.parse(string, Instant.from(_))

}
