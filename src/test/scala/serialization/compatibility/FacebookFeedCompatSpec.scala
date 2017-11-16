package serialization.compatibility

import base._
import domain.feed._
import serialization.FacebookDecoders._

class FacebookFeedCompatSpec extends SyncSpec with JsonSerializationSupport {

  val feedPath = "testdata/me_feed.json"

  "FacebookFeed" should {
    s"be compatible with $feedPath" in {
      decodeJson[FacebookFeed](feedPath) shouldBe TestEntities.feed
    }
  }
}
