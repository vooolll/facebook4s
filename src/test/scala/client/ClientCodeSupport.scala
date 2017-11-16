package client

import domain.oauth.FacebookClientCode

class ClientCodeSupport extends FacebookClientSupport {
  "Facebook Graph Api" should {
    "obtain client code by long lived token" in { c =>
      c.mockSendWithResource(resourcePath = "testdata/client_code.json")
      c.clientCode("long lived token") map(
        _ shouldBe FacebookClientCode("test-test-test-test", Some("machine id")))
    }
  }
}
