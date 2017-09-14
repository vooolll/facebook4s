package domain

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mockito.MockitoSugar

class FixedSizeValueSpec extends WordSpec with Matchers with MockitoSugar {

  private case class TestValue(value: String) extends FixedSizeValue(27)

  "throw exception on value not equal to 27" in {
    an[IllegalArgumentException] shouldBe thrownBy(TestValue("123"))
  }

}
