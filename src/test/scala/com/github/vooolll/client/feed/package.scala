package com.github.vooolll.client

import java.net.URL

import com.github.vooolll.base._
import com.github.vooolll.config.FacebookConfig
import com.github.vooolll.domain.comments.{FacebookComment, FacebookCommentId, FacebookComments}
import com.github.vooolll.domain.feed.{FacebookFeed, FacebookFeedPaging}
import com.github.vooolll.domain.friends.FacebookFriends
import com.github.vooolll.domain.media._
import com.github.vooolll.domain.posts.{FacebookPost, FacebookPostId}
import com.github.vooolll.domain.profile._

package object feed {
  val postId = FacebookPostId("117656352360395_120118735447490")
  val commentId = FacebookCommentId("120118675447496_170608873731809")

  val userId = FacebookUserId("117656352360395")

  val v = FacebookConfig.version.value

  val post = FacebookPost(
    id = FacebookPostId("117656352360395_214427839349912"),
    name = None,
    message = Some("hala"),
    createdTime = Some(toInstant("2018-05-30T06:16:31+0000")),
    objectId = None,
    picture = None,
    from = Some(FacebookProfileId("117656352360395")),
    attachments = Nil)

  val post1 = FacebookPost(
    id = postId,
    name = Some("Bob's cover photo"),
    message = None,
    createdTime = Some(toInstant("2017-12-19T14:08:44+0000")),
    objectId = Some("120118675447496"),
    picture = Some(new URL("https://scontent.xx.fbcdn.net/v/t1.0-0/s130x130/25398995_120118675447496_5830741756468130361_n.jpg")),
    from = Some(FacebookProfileId("117656352360395")),
    attachments = List(FacebookAttachment(
      attachment = Some(FacebookImageSource(height = 360, src = new URL(
        "https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/25398995_120118675447496_5830741756468130361_n.jpg"
      ), width = 720)),
      target = FacebookAttachmentTarget(
        id = FacebookAttachmentId("100023480929454"),
        url = new URL("https://www.facebook.com/bob.willins.98")
      ),
      title = Some("Bob's cover photo"),
      attachmentType = AttachmentTypes.CoverPhoto,
      url = new URL("https://www.facebook.com/bob.willins.98")
    )))

  val post2 = FacebookPost(
    id = FacebookPostId("117656352360395_117607245698639"),
    name = Some("Bob Willins"),
    message = None,
    createdTime = Some(toInstant("2017-12-18T11:30:10+0000")),
    objectId = Some("117607225698641"),
    picture = Some(new URL("https://scontent.xx.fbcdn.net/v/t1.0-0/s130x130/25396081_117607225698641_6348338142026249400_n.jpg")),
    from = Some(FacebookProfileId("117656352360395")),
    attachments = List(
      FacebookAttachment(
        attachment = Some(FacebookImageSource(height = 360, src = new URL(
          "https://scontent.xx.fbcdn.net/v/t1.0-9/q82/s720x720/25396081_117607225698641_6348338142026249400_n.jpg"
        ), width = 720)),
        target = FacebookAttachmentTarget(
          id = FacebookAttachmentId("100023480929454"),
          url = new URL("https://www.facebook.com/bob.willins.98")
        ),
        title = Some("Bob Willins"),
        attachmentType = AttachmentTypes.ProfileMedia,
        url = new URL("https://www.facebook.com/bob.willins.98")
      )
    ))

  val post3 = FacebookPost(
    id = FacebookPostId("117656352360395_117427439049953"),
    name = Some("Born on December 18, 1992"),
    message = None,
    createdTime = Some(toInstant("2017-12-18T09:38:18+0000")),
    objectId = Some("117427432383287"),
    picture = None,
    from = Some(FacebookProfileId("117656352360395")),
    attachments = List(
      FacebookAttachment(
        attachment = None,
        target = FacebookAttachmentTarget(
          id = FacebookAttachmentId("117427432383287"),
          url = new URL("https://www.facebook.com/100023480929454/posts/117427432383287/")
        ),
        title = Some("Born on December 18, 1992"),
        attachmentType = AttachmentTypes.LifeEvent,
        url = new URL("https://www.facebook.com/100023480929454/posts/117427432383287/")
      )
    ))

  val paging = FacebookFeedPaging(
    Some(new URL(s"https://graph.facebook.com/v$v/117656352360395/feed")),
    Some(new URL(s"https://graph.facebook.com/v$v/117656352360395/feed")))

  val feed = FacebookFeed(List(post, post1, post2, post3), paging)


  implicit class FacebookPostWithoutQuery(post: FacebookPost) {
    def withoutQueryParams = post.copy(
      picture = post.picture.map(withoutQuery),
      attachments = post.attachments.map(_.withoutQueryParams)
    )
  }

  implicit class FacebookFeedWithoutQuery(feed: FacebookFeed) {
    def pictureWithoutQueryParams = feed.copy(
      posts  = feed.posts.map(_.withoutQueryParams),
      paging = feed.paging.withoutQueryParams
    )
  }


  implicit class FacebookFeedPagingWithoutQuery(paging: FacebookFeedPaging) {
    def withoutQueryParams: FacebookFeedPaging = {
      paging.copy(
        next     = paging.next.map(withoutQuery),
        previous = paging.previous.map(withoutQuery))
    }
  }

  implicit class FacebookUserWithoutQuery(user: FacebookUser) {
    def withoutQueryParams = {
      user.copy(
        picture = user.picture.map(withoutPictureQuery),
        link = user.link.map(l => new URL(l.getProtocol + "://" + l.getHost))) //TODO facebook4s-117
    }
  }

  implicit class FacebookFriendsWithoutQuery(facebookFriends: FacebookFriends) {
    def withoutQueryParams = {
      facebookFriends.copy(friends = facebookFriends.friends.map(_.withoutQueryParams))
    }
  }

  implicit class FacebookCommentWithoutQuery(comment: FacebookComment) {
    def withoutQueryParams = withoutAttachmentQuery(comment)
  }

  implicit class FacebookCommenstWithoutQuery(c: FacebookComments) {
    def withoutQueryParams = c.copy(comments = withoutCommentsQuery(c))
  }

  implicit class FacebookAttachmentWithoutQuery(attach: FacebookAttachment) {
    def withoutQueryParams = attach.copy(
      attachment = attach.attachment.map(a => a.copy(src = withoutQuery(a.src)))
    )
  }

  private[this] def withoutPictureQuery(pic: FacebookUserPicture) = pic.copy(url = withoutQuery(pic.url))
  private[this] def withoutAttachmentQuery(comment: FacebookComment) = comment.copy(attachment = comment.attachment.map(
    a => a.copy(attachment = a.attachment.map(v => v.copy(src = withoutQuery(v.src))))
  ))

  private[this] def withoutCommentsQuery(c: FacebookComments) = {
    c.comments.map(comment => withoutAttachmentQuery(comment))
  }


  private[this] def withoutQuery(u: URL) = {
    new URL(u.toString.takeWhile(_ != '?'))
  }

}
