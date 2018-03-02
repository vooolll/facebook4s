package client.feed

import base.FacebookClientSupport
import base.TestConfiguration.userTokenRaw
import cats.implicits._
import domain.FacebookOrder
import domain.comments._
import domain.likes.FacebookPaging
import domain.profile.FacebookProfileId
import serialization.compatibility.toInstant
import FacebookCommentAttributes._

class CommentSpec extends FacebookClientSupport {

  val comment = FacebookComment(
    id = FacebookCommentId("120118675447496_128078554651508"),
    message = "Super comment".some,
    createdTime = Some(toInstant("2017-12-25T10:23:54+0000")),
    from = FacebookProfileId("117656352360395").some,
    None)

  val commentPaging = FacebookPaging(
    "WTI5dGJXVnVkRjlqZAFhKemIzSTZANVEk0TURjNE5UVTBOalV4TlRBNE9qRTFNVFF4T1RjME16UT0ZD".some,
    "WTI5dGJXVnVkRjlqZAFhKemIzSTZANVEk0TURjNE5UVTBOalV4TlRBNE9qRTFNVFF4T1RjME16UT0ZD".some)

  val comments = FacebookComments(List(comment), commentPaging.some)

  val commentSummary = FacebookCommentSummary(
    order = FacebookOrder.Chronological,
    totalCount = 1,
    canComment = true.some)

  val commentsWithSummary = comments.copy(summary = commentSummary.some)
  val commentReplyWithSummary = FacebookComment(
    FacebookCommentId("120118675447496_170608873731809"),
    Some("Another comment"),
    Some(toInstant("2018-03-02T13:31:32+0000")),
    Some(FacebookProfileId("117656352360395")),
    Some(FacebookComment(FacebookCommentId("120118675447496_128078554651508"),
      Some("Super comment"),
      Some(toInstant("2017-12-25T10:23:54+0000")),
      Some(FacebookProfileId("117656352360395")), None)))

  "Facebook Graph Api" should {
    "return comments of post" in { c =>
      c.comments(postId, userTokenRaw) map (_ shouldBe comments)
    }

    "return comments of post result" in { c =>
      c.commentsResult(postId, userTokenRaw) map (_ shouldBe comments.asRight)
    }

    "return comments of post with summary" in { c =>
      c.comments(postId, userTokenRaw, summary = true) map (_ shouldBe commentsWithSummary)
    }

    "return comments of post result with summary" in { c =>
      c.commentsResult(postId, userTokenRaw, summary = true) map (_ shouldBe commentsWithSummary.asRight)
    }

    "return comment of comment" in { c =>
      c.comment(commentId, userTokenRaw, defaultCommentAttributeValues) map (_ shouldBe commentReplyWithSummary)
    }

    "return comment of comment result" in { c =>
      c.commentResult(
        commentId, userTokenRaw, defaultCommentAttributeValues) map (_ shouldBe commentReplyWithSummary.asRight)
    }
  }
}
