package domain.feed

import java.time.Instant

final case class FacebookUserFeed(data: List[FacebookPost], paging: FacebookPaging)

final case class FacebookPost(id: String, story: String, createdTime: Instant)

final case class FacebookPaging(
  next: Option[String],
  previous: Option[String]
)