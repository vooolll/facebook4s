package domain.feed

import domain.posts.FacebookPost

final case class FacebookFeed(posts: List[FacebookPost], paging: FacebookPaging)

final case class FacebookPaging(
  next     : Option[String],
  previous : Option[String])