package domain.permission

object FacebookPermissions {

  /**
    * Trait that represents facebook permission - https://developers.facebook.com/docs/facebook-login/permissions/
    */
  sealed trait FacebookUserPermission {
    val value: String
  }

  /**
    * Facebook permission that gives access to user posts.
    */
  final case object FacebookUserPosts extends FacebookUserPermission {
    val value = "user_posts"
  }
}



