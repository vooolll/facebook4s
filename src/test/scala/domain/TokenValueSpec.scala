package domain

import org.scalatest.{Matchers, WordSpec}
import org.scalatest.mockito.MockitoSugar

class TokenValueSpec extends WordSpec with Matchers with MockitoSugar {

  val testValue = "1234567891011121|A6BCDEFiGASDFdB1_Zviht7lzxc"

  "TokenValue#fromRaw" should {
    s"create TokenValue from raw value in format = $testValue" in {
      TokenValue.fromRaw(testValue) shouldBe TokenValue("A6BCDEFiGASDFdB1_Zviht7lzxc")
    }
  }

}
