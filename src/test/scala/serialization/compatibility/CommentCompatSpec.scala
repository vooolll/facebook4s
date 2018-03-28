package serialization.compatibility

import cats.implicits._
import domain.comments.{FacebookComment, FacebookCommentSummary, FacebookComments}
import serialization.FacebookDecoders._

class CommentCompatSpec extends CompatibilitySpec {

  val commentPath = "testdata/comment.json"
  val commentsPath = "testdata/comments.json"
  val commentSummaryPath = "testdata/comment_summary.json"
  val commentsWithSummaryPath = "testdata/comment_with_summary.json"

  "FacebookComment" should {
    s"be compatible with $commentPath" in {
      decodeJson[FacebookComment](commentPath) shouldBe comment
    }

    s"be compatible with $commentsPath" in {
      decodeJson[FacebookComments](commentsPath) shouldBe comments
    }

    s"be compatible with $commentSummaryPath" in {
      decodeJson[FacebookCommentSummary](commentSummaryPath) shouldBe commentSummary
    }

    s"be compatible with $commentsWithSummaryPath" in {
      decodeJson[FacebookComments](commentsWithSummaryPath) shouldBe comments.copy(
        summary = commentSummary.some)
    }

  }
}
