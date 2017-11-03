package domain.permission

object FacebookPermissions {
  sealed trait FacebookUserPermission {
    val value: String
  }

  final case object FacebookUserPosts extends FacebookUserPermission {
    val value = "user_posts"
  }
}



