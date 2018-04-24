package domain.friends

import domain.FacebookPaging
import domain.profile.FacebookUser

final case class FacebookFriends(
  friends: List[FacebookUser],
  paging: Option[FacebookPaging],
  summary: Option[FacebookFriendsSummary])

final case class FacebookFriendsSummary(totalCount: Int)