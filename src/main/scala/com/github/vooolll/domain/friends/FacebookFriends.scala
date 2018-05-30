package com.github.vooolll.domain.friends

import com.github.vooolll.domain.FacebookPaging
import com.github.vooolll.domain.profile.FacebookUser

final case class FacebookFriends(
  friends: List[FacebookUser],
  paging: Option[FacebookPaging],
  summary: Option[FacebookFriendsSummary])

final case class FacebookFriendsSummary(totalCount: Int)