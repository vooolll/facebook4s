package serialization.compatibility

import domain.feed._
import serialization.FacebookDecoders._

class FeedCompatSpec extends CompatibilitySpec {

  val feedPath = "testdata/me_feed.json"

  "FacebookFeed" should {
    s"be compatible with $feedPath" in {
      decodeJson[FacebookFeed](feedPath) shouldBe feed
    }
  }
}
