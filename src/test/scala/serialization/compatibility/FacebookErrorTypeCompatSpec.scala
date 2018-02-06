package serialization.compatibility

import base.{JsonSerializationSupport, SyncSpec}
import domain.oauth.FacebookError
import io.circe.Encoder
import io.circe.generic.semiauto._
import io.circe.syntax._
import io.circe.parser.parse
import cats.implicits._
import serialization.FacebookDecoders._

class FacebookErrorTypeCompatSpec extends SyncSpec with JsonSerializationSupport {

  val codes = FacebookError.values.map(_.code)

  case class TestObject(message: String, code: Int)

  implicit val decoder: Encoder[TestObject] = deriveEncoder[TestObject]

  "FacebookErrorType" should {
    "be compatible with all facebook codes" in {
      codes.map { code =>
        decodeStringJson(TestObject("any", code).asJson.toString())(decodeError)
      } shouldBe FacebookError.values.map(t => FacebookError("any", t).asRight)
    }
  }

}
