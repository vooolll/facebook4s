package com.github.vooolll.client.feed

import java.net.URL

import com.github.vooolll.base.TestConfiguration.userTokenRaw
import cats.implicits._
import com.github.vooolll.domain.{FacebookOrder, FacebookPaging}
import com.github.vooolll.domain.comments._
import com.github.vooolll.domain.profile.FacebookProfileId
import com.github.vooolll.base._
import FacebookCommentAttributes._
import com.github.vooolll.domain.media._

class CommentSpec extends FacebookClientSupport {

  val mediaObject = Some(
    FacebookMediaObject(FacebookMediaObjectId("120118675447496"), toInstant("2017-12-19T14:08:45+0000")))
  val url = new URL("https://www.facebook.com/photo.php?fbid=173295196796510&set=p.173295196796510&type=3")

  val attachment = Some(FacebookAttachment(
    FacebookImageSource(720.0,
      new URL("https://scontent.xx.fbcdn.net/v/t31.0-8/s720x720/28617069_173295196796510_8133139076598269923_o.jpg"), 549.0),
    FacebookAttachmentTarget(FacebookAttachmentId("173295196796510"), url), url, AttachmentTypes.Photo))

  val comment = FacebookComment(
    id = FacebookCommentId("120118675447496_128078554651508"),
    message = "Super comment".some,
    createdTime = Some(toInstant("2017-12-25T10:23:54+0000")),
    from = FacebookProfileId("117656352360395").some,
    parent = None,
    mediaObject = mediaObject,
    attachment = attachment)
  
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
      Some(FacebookProfileId("117656352360395")), None, None, None)),
    mediaObject, None
  )

  "Facebook Graph Api" should {
    "return comments of post" in { c =>
      c.comments(postId, userTokenRaw) map (_.withoutQueryParams shouldBe comments)
    }

    "return comments of post result" in { c =>
      c.commentsResult(postId, userTokenRaw) map (_.map(_.withoutQueryParams) shouldBe comments.asRight)
    }

    "return comments of post with summary" in { c =>
      c.comments(postId, userTokenRaw, summary = true) map (_.withoutQueryParams shouldBe commentsWithSummary)
    }

    "return comments of post result with summary" in { c =>
      c.commentsResult(postId, userTokenRaw, summary = true) map (_.map(_.withoutQueryParams) shouldBe commentsWithSummary.asRight)
    }

    "return comment of comment" in { c =>
      c.comment(commentId, userTokenRaw, defaultCommentAttributeValues) map (_.withoutQueryParams shouldBe commentReplyWithSummary)
    }

    "return comment of comment result" in { c =>
      c.commentResult(
        commentId, userTokenRaw, defaultCommentAttributeValues) map (_.map(_.withoutQueryParams) shouldBe commentReplyWithSummary.asRight)
    }
  }
}
