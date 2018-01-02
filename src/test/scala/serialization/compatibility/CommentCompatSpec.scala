package serialization.compatibility

import base.{JsonSerializationSupport, SyncSpec}
import domain.comments.{FacebookComment, FacebookCommentSummary, FacebookComments}
import serialization.FacebookDecoders._
import cats.implicits._

class CommentCompatSpec extends SyncSpec with JsonSerializationSupport {

  val commentPath = "testdata/comment.json"
  val commentsPath = "testdata/comments.json"
  val commentSummaryPath = "testdata/comment_summary.json"
  val commentsWithSummaryPath = "testdata/comment_with_summary.json"

  "FacebookComment" should {
    s"be compatible with $commentPath" in {
      decodeJson[FacebookComment](commentPath).map(_ shouldBe comment)
    }

    s"be compatible with $commentsPath" in {
      decodeJson[FacebookComments](commentsPath).map(_ shouldBe comments)
    }

    s"be compatible with $commentSummaryPath" in {
      decodeJson[FacebookCommentSummary](commentSummaryPath).map(_ shouldBe commentSummary)
    }

    s"be compatible with $commentsWithSummaryPath" in {
      decodeJson[FacebookComments](commentsWithSummaryPath).map(_ shouldBe comments.copy(
        summary = commentSummary.some))
    }

  }
}
