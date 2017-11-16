package serialization.compatibility

import base._
import domain.oauth._
import io.circe._
import io.circe.parser._
import cats.syntax.either._
import scala.concurrent.duration._

class FacebookAccessTokenCompatSpec extends SyncSpec with JsonSerializationSupport {
  import serialization.FacebookDecoders._

  val userAccessTokenPath = "testdata/user_access_token.json"
  val appAccessTokenPath = "testdata/app_access_token.json"

  "FacebookAccessToken" should {
    s"be compatible with $userAccessTokenPath" in {
      val decoded = decodeJson[FacebookAccessToken](userAccessTokenPath)(decodeUserAccessToken)
      decoded shouldBe TestEntities.userAccessToken.asRight
    }

    s"be compatible with $appAccessTokenPath" in {
      val decoded = decodeJson[FacebookAccessToken](appAccessTokenPath)(decodeAppAccessToken)
      decoded shouldBe TestEntities.appAccessToken.asRight
    }
  }

}

trait JsonSerializationSupport {
  import client.ClientProbe._

  def decodeJson[T](path: String)(implicit d: Decoder[T]) = parse(readFile(path)).right.get.as[T]
}

object TestEntities {
  val userAccessToken = FacebookAccessToken(
    TokenValue("test token"), UserAccessToken("bearer", 5107587.seconds))

  val appAccessToken = FacebookAccessToken(
    TokenValue("1234567891011121|A6BCDEFiGASDFdB1_Zviht7lzxc"), AppAccessToken("bearer"))
}