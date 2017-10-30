package domain.permission

object FacebookPermissions {
  sealed trait FacebookUserPermission

  final case object UserPosts extends FacebookUserPermission {
    val value = "user_posts"
  }
}



