package services

import cats.implicits._
import config.FacebookConfig._
import org.scalatest.{Matchers, WordSpec}
import other.FacebookShowOps._

class UriServiceSpec extends WordSpec with Matchers {

  val s = UriService()

  "UriService" should {
    "return url to obtain log lived uri" in {
      s.longLivedTokenUri("test").toString() shouldBe "https://graph.facebook.com/v2.10/oauth/access_token" +
        s"?client_id=${clientId.show}" +
        s"&client_secret=${appSecret.show}" +
        s"&grant_type=fb_exchange_token" +
        s"&fb_exchange_token=test"
    }

    "return auth uri" in {
      s.authUrl(Seq.empty).toString() shouldBe s"https://graph.facebook.com/v2.10/dialog/oauth" +
        s"?client_id=${clientId.show}" +
        s"&redirect_uri=http%3A%2F%2Flocalhost%3A9000%2Fredirect"
    }
  }

}
