package serialization.compatibility

import base._
import domain.oauth._
import serialization.FacebookDecoders._

class FacebookAccessTokenCompatSpec extends SyncSpec with JsonSerializationSupport {

  val userAccessTokenPath = "testdata/user_access_token.json"
  val appAccessTokenPath = "testdata/app_access_token.json"

  "FacebookAccessToken" should {
    s"be compatible with $userAccessTokenPath" in {
      val decoded = decodeJson[FacebookAccessToken](userAccessTokenPath)(decodeUserAccessToken)
      decoded shouldBe TestEntities.userAccessToken
    }

    s"be compatible with $appAccessTokenPath" in {
      val decoded = decodeJson[FacebookAccessToken](appAccessTokenPath)(decodeAppAccessToken)
      decoded shouldBe TestEntities.appAccessToken
    }
  }

}
