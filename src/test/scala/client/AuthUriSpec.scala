package client

import base.{FacebookClientStubSupport, TestUrls}
import domain.permission.FacebookPermissions.FacebookPermission
import domain.permission.FacebookPermissions.UserDataPermissions.Posts

class AuthUriSpec extends FacebookClientStubSupport {
  val emptyPermissions = Seq.empty[FacebookPermission]
  val permissions = Seq(Posts)
  "Facebook Graph Api" should {
    "obtain client code by long lived token" in { c =>
      c.buildAuthUrl(emptyPermissions).toString() shouldBe TestUrls.buildAuthUrl(emptyPermissions).toString()
    }

    "obtain client code by long lived token with permission" in { c =>
      c.buildAuthUrl(permissions).toString() shouldBe TestUrls.buildAuthUrl(permissions).toString()
    }
  }
}
