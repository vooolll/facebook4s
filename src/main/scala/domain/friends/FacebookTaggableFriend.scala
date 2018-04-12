package domain.friends

import domain.FacebookPaging
import domain.profile.FacebookUserPicture

case class FacebookTaggableFriendId(value: String)

case class FacebookTaggableFriend(id: FacebookTaggableFriendId, name: Option[String], picture: Option[FacebookUserPicture])

case class FacebookTaggableFriends(taggableFriens: List[FacebookTaggableFriend], paging: Option[FacebookPaging])