package services

import domain.{FacebookAppSecret, FacebookClientId}
import org.scalatest.{Matchers, WordSpec}

class UriServiceSpec extends WordSpec with Matchers {

  "UriService" should {
    "return url to obtain log lived uri" in {
      val s = UriService(
        FacebookClientId("1234561432757091"), FacebookAppSecret("41725f9990f489d5f1b1533a77a17263"))
      s.longLivedTokenUri("test").toString() shouldBe "https://graph.facebook.com/v2.10/oauth/access_token" +
        "?client_id=1234561432757091&client_secret=41725f9990f489d5f1b1533a77a17263" +
        "&grant_type=fb_exchange_token&fb_exchange_token=test"
    }
  }

}
