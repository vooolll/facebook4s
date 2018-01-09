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

  val codes = Set(101, 102, 1, 2, 4, 17, 10, 190, 341, 368, 506, 1609005, 100, 200)

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
