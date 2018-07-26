package com.github.vooolll.client

import com.github.vooolll.base.{FacebookClientStubSupport, TestUrls}
import com.github.vooolll.domain.permission.FacebookPermissions.FacebookPermission
import com.github.vooolll.domain.permission.FacebookPermissions.UserDataPermissions.Posts

class AuthUriSpec extends FacebookClientStubSupport {
  val emptyPermissions = Set.empty
  val permissions = Set(Posts)
  "Facebook Graph Api" should {
    "obtain client code by long lived token" in { c =>
      c.buildAuthUrl(emptyPermissions).toString() shouldBe TestUrls.buildAuthUrl(emptyPermissions).toString()
    }

    "obtain client code by long lived token with permission" in { c =>
      c.buildAuthUrl(permissions).toString() shouldBe TestUrls.buildAuthUrl(permissions).toString()
    }
  }
}
