package client

import domain.oauth.FacebookClientCode

class ClientCodeSpec extends FacebookClientSpec {
  "Facebook Graph Api" should {
    "obtain client code by long lived token" in { c =>
      c.mockSendWithResource(resourcePath = "testdata/access_token_code.json")
      c.clientCode("long lived token") map(
        _ shouldBe FacebookClientCode("test-test-test-test", Some("machine id")))
    }
  }
}
