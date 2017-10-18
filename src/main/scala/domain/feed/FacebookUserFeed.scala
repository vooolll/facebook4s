package domain.feed

import org.joda.time.Instant


final class FacebookUserFeed(data: List[Post], paging: Paging)

final case class Post(id: Long, story: String, createdTime: Instant)

final case class Paging(
  next: Option[String],
  previous: Option[String]
)