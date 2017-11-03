package api

import services.UriService

class AuthUriSpec extends FacebookClientSpec {
  "Facebook Graph Api" should {
    "obtain client code by long lived token" in { c =>
      c.authUrl() shouldBe UriService().authUrl().toString()
    }
  }
}
