package services

import akka.http.scaladsl.model.StatusCodes
import config.FacebookConfig.{version, clientId, appSecret}
import domain.FacebookShowOps._
import cats.implicits._
import config.FacebookConstants.{host, oauthUri}
import org.f100ded.scalaurlbuilder.URLBuilder
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncWordSpec, Matchers}

class AsyncRequestServiceSpec extends AsyncWordSpec with Matchers with MockitoSugar {
  "Should send request" in {
    val url = URLBuilder(base = host)
      .withPathSegments(version.show, oauthUri)
      .withQueryParameters(
        "client_id"     -> clientId.show,
        "client_secret" -> appSecret.show,
        "grant_type"    -> "client_credentials"
      )
    val asyncRequestService = new AsyncRequestService
    asyncRequestService.sendRequest(url).map(_.status shouldBe StatusCodes.OK)
  }
}
