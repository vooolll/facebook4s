package domain.permission

object FacebookPermissions {
  sealed trait FacebookPermission

  final case object UserPosts extends FacebookPermission {
    val value = "user_posts"
  }
}



