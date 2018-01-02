package domain.feed

import domain.posts.FacebookPost

final case class FacebookFeed(posts: List[FacebookPost], paging: FacebookFeedPaging)

final case class FacebookFeedPaging(
  next     : Option[String],
  previous : Option[String])