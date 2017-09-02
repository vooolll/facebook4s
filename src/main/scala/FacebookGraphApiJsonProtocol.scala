package spracebook

object FacebookGraphApiJsonProtocol {
  
  case class TokenData(
    app_id: Long, 
    is_valid: Boolean, 
    application: String,
    user_id: Long, 
    issued_at: Option[Long], 
    expires_at: Long, 
    scopes: Seq[String]
  )

  case class TokenDataWrapper(data: TokenData)

  case class AccessToken(
    access_token: String, 
    expires: Long
  )

  case class Image(
    width: Int,
    height: Int,
    source: String
  )

  object Image {
    implicit val ordering: Ordering[Image] = new Ordering[Image] {
      def compare(x: Image, y: Image): Int = (y.width*y.height) compare (x.width*x.height)
    }
  }

  case class Photo(
    id: String,
    name: Option[String], //this is the photo caption
    images: Seq[Image]
  )

  case class Cursors(
    after: String,
    before: String
  )

  case class Paging(
    cursors: Option[Cursors],
    next: Option[String],
    previous: Option[String]
  )

  case class Response[T](
    data: Seq[T],
    paging: Option[Paging]
  )

  case class UserProfilePic (url: String, is_silhouette: Boolean)
  case class UserProfilePicContainer (data: UserProfilePic)

  case class User(
    id: String,
    username: Option[String],
    name: Option[String],
    first_name: Option[String],
    middle_name: Option[String],
    last_name: Option[String],
    email: Option[String],
    link: Option[String],
    gender: Option[String],
    picture: Option[UserProfilePicContainer]
  ) {
    // Ignores Facebook default photo
    def profilePic: Option[String] = picture.flatMap(p => if (p.data.is_silhouette) None else Some(p.data.url))
  }

  case class FacebookFriends (data: Seq[User])

  case class Page(id: String, name: String, link: String)

  case class Tab(id: String, name: String, link: String)

  //{"id":"100914593450999","photos":["100914610117664"]}
  case class CreatedStory(
    id: String,
    photos: Seq[String]
  )

  //{"id":"4286226694008_1953664"}
  case class CreatedComment(id: String)

  case class Comment(
    id: String,
    from: User,
    message: String,
    can_remove: Boolean,
    created_time: String,
    like_count: Int,
    user_likes: Boolean
  )

  case class Properties(
    name: String,
    text: String,
    href: String
  )

  case class Share(
    id: String,
    from: User,
    message: String,
    story: String,
    picture: String,
    link: String,
    name: String, //album name
    caption: String,
    properties: Seq[Properties],
    status_type: String,
    object_id: String,
    created_time: String
  )

  case class Insight(
    id: String,
    name: String,
    period: String,
    values: Seq[InsightDataPoint],
    title: String,
    description: String
  )

  case class InsightValue(
    action_type_id: Long,
    action_type_name: String,
    object_type_id: Long,
    object_type_name: String,
    value: Int
  )

  case class InsightDataPoint(
    value: Seq[InsightValue],
    end_time: String
  )

  case class Error(message: String, `type`: String, code: Int, error_subcode: Option[Int])
  case class ErrorResponse(error: Error)
}
