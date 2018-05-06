package serialization.compatibility

import base._
import cats.implicits._
import domain.{FacebookOrder, FacebookPaging}
import domain.comments._
import domain.profile.FacebookProfileId
import serialization.FacebookDecoders._

class CommentCompatSpec extends CompatibilitySpec {

  val commentPath = "testdata/comment.json"
  val commentsPath = "testdata/comments.json"
  val commentSummaryPath = "testdata/comment_summary.json"
  val commentsWithSummaryPath = "testdata/comment_with_summary.json"

  val comment = FacebookComment(
    id = FacebookCommentId("120118675447496_128078554651508"),
    message = "Super comment".some,
    createdTime = Some(toInstant("2017-12-25T10:23:54+0000")),
    from = FacebookProfileId("117661112359919").some,
    parent = None,
    mediaObject = Some(FacebookMediaObject(FacebookMediaObjectId("120118675447496"), toInstant("2017-12-19T14:08:45+0000"))),
    attachment = None)

  val commentPaging = FacebookPaging(
    "WTI5dGJXVnVkRjlqZAFhKemIzSTZANVEk0TURjNE5UVTBOalV4TlRBNE9qRTFNVFF4T1RjME16UT0ZD".some,
    "WTI5dGJXVnVkRjlqZAFhKemIzSTZANVEk0TURjNE5UVTBOalV4TlRBNE9qRTFNVFF4T1RjME16UT0ZD".some)

  val comments = FacebookComments(List(comment), commentPaging.some)

  val commentSummary = FacebookCommentSummary(
    order = FacebookOrder.Chronological,
    totalCount = 1,
    canComment = true.some)

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
