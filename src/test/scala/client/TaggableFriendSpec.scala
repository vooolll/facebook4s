package client

import java.net.URL

import base.FacebookClientSupport
import domain.FacebookPaging
import domain.friends.{FacebookTaggableFriend, FacebookTaggableFriendId, FacebookTaggableFriends}
import domain.profile.FacebookUserPicture

import scala.concurrent.Future

class TaggableFriendSpec extends FacebookClientSupport {

  val userPicture = FacebookUserPicture(50, isSilhouette = false, new URL("https://lookaside.facebook.com/" +
    "platform/profilepic/?token=AaLlPVfUEHkm9xWbMUZQtDpm0brZQsBu-zXm7iuV8Ed8QI7JhZVvJS6LCo4Nm-" +
    "qPHJCA30dJIJ6kvEmQP-tXAsj1H4RRmyaX_hHgy0bQOcJY7A&tag=1&height=50&width=50&ext=1522938580&hash=AeQuAwKpCMCU_G4d"), 50)
  val taggableFriend = FacebookTaggableFriend(FacebookTaggableFriendId("AaIjDECkFPXyF7R9N-t9ww7UnMauWLygCDRSsffGTDfC00Htau5Ci" +
    "JurKnh1FdlEVli9Ae5vWYsfVVzRwEVozrj6ouKNtrvlX82s5fLFEEaqSw"), Some("Valeryi Baibossynov"), Some(userPicture))

  val paging = FacebookPaging(
    Some("QWFJYzdrZAW0tM3IzREYyZAG85ekRySnRCbTJNeTVTNzhYTnBHN2dRV2xkT2VCVC1PNjh6Uzk1bGhmWUtlSl9OVGJDOFBGNlFVelY4UFkzQTRicFJNQ1pmenJVREVtekVROXlWV1Q5MG9GRk1wUVEZD"),
    Some("QWFMRFR1c3czVUJHWUpIWHQyaUNZAS1ZA6aThaN016TUtwa00ySkxOd212UmwxbUk0dFNBTkhVd3hmcXlSRUxuU2wyYmlGY2hWOHNLWEVKWkZAlSk9DdzhNLWpJc1NQTVFHWUlzZAW9ySDJ2Yk5TYWcZD"))
  val taggableFriends = FacebookTaggableFriends(List(taggableFriend), Some(paging))

  "Facebook Graph Api" should {
    "return taggable_friend" in { c =>
      Future.successful(fail())
    }
  }

}
