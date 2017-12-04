package domain.posts

import java.time.Instant

final case class FacebookPostId(value: String)

final case class FacebookPost(
  id          : FacebookPostId,
  story       : String,
  createdTime : Instant)
