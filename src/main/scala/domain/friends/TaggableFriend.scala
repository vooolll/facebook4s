package domain.friends

import domain.FacebookPaging
import domain.profile.FacebookUserPicture

case class TaggableFriendId(value: String)

case class TaggableFriend(id: TaggableFriendId, name: Option[String], picture: Option[FacebookUserPicture])

case class TaggableFriends(taggableFriens: List[TaggableFriend], paging: Option[FacebookPaging])