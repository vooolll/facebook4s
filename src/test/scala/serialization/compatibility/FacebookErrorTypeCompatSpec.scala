package serialization.compatibility

import domain.oauth.FacebookError
import io.circe.Encoder
import io.circe.generic.semiauto._
import io.circe.syntax._
import serialization.FacebookDecoders._

class FacebookErrorTypeCompatSpec extends CompatibilitySpec {

  val codes = FacebookError.values.map(_.code)

  case class TestObject(message: String, code: Int)
  case class TestFacebookError(error: TestObject)

  implicit val decodeObject: Encoder[TestObject] = deriveEncoder[TestObject]
  implicit val decoderTestError: Encoder[TestFacebookError] = deriveEncoder[TestFacebookError]

  "FacebookErrorType" should {
    "be compatible with all facebook codes" in {
      codes.map { code =>
        decodeStringJson(TestFacebookError(TestObject("any", code)).asJson.toString())(decodeError)
      } shouldBe FacebookError.values.map(t => FacebookError("any", t))
    }
  }

}
