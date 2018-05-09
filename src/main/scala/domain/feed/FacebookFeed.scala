package domain.feed

import java.net.URL

import domain.posts.FacebookPost

final case class FacebookFeed(posts: List[FacebookPost], paging: FacebookFeedPaging)

final case class FacebookFeedPaging(
  next     : Option[URL],
  previous : Option[URL])