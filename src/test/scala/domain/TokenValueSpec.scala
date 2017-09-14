package domain

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mockito.MockitoSugar

class TokenValueSpec extends WordSpec with Matchers with MockitoSugar {

  val testValue = "1969406143212345|H6FF85UiGASDFdB1_Zviht7lzxc"

  "TokenValue" should {
    s"create TokenValue from raw value = $testValue" in {
      TokenValue.fromRaw(testValue) shouldBe TokenValue("H6FF85UiGASDFdB1_Zviht7lzxc")
    }

    s"fromRaw cannot have length less then ${TokenValue.length}" in {
      an[IllegalArgumentException] shouldBe thrownBy(TokenValue.fromRaw("asd"))
    }
  }

}
