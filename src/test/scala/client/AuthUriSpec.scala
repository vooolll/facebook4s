package client

import domain.permission.FacebookPermissions.{FacebookUserPermission, FacebookUserPosts}
import services.UriService

class AuthUriSpec extends FacebookClientSpec {
  val emptyPermissions = Seq.empty[FacebookUserPermission]
  val permissions = Seq(FacebookUserPosts)
  "Facebook Graph Api" should {
    "obtain client code by long lived token" in { c =>
      c.authUrl(emptyPermissions) shouldBe UriService().authUrl(emptyPermissions).toString()
    }

    "obtain client code by long lived token with permission" in { c =>
      c.authUrl(permissions) shouldBe UriService().authUrl(permissions).toString()
    }
  }
}
