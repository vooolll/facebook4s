package domain.posts

import java.time.Instant

final case class FacebookPost(
  id          : String,
  story       : String,
  createdTime : Instant)
