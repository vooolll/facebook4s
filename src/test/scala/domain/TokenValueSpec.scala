package domain

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mockito.MockitoSugar

class TokenValueSpec extends WordSpec with Matchers with MockitoSugar {

  val testValue = "1969406143212345|H6FF85UiGASDFdB1_Zviht7lzxc"

  "TokenValue#fromRaw" should {
    s"create TokenValue from raw value in format = $testValue" in {
      TokenValue.fromRaw(testValue) shouldBe TokenValue("H6FF85UiGASDFdB1_Zviht7lzxc")
    }
  }

}
