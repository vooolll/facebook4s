package com.github.vooolll.domain.permission

import com.github.vooolll.domain.FacebookAttribute

object FacebookPermissions {

  /**
    * Trait that represents facebook permission - https://developers.facebook.com/docs/facebook-login/permissions/
    */
  sealed trait FacebookPermission extends FacebookAttribute {
    val value: String
  }

  object UserDataPermissions {
    val values = Set(
      Email,
      AgeRange,
      BirthDay,
      Fiends,
      Hometown,
      Gender,
      Likes,
      Location,
      Photos,
      Videos,
      Status,
      TaggedPlaces,
      Posts
    )

    final case object Email extends FacebookPermission {
      override val value = "email"
    }

    final case object AgeRange extends FacebookPermission {
      override val value = "user_age_range"
    }

    final case object BirthDay extends FacebookPermission {
      override val value = "user_birthday"
    }

    final case object Fiends extends FacebookPermission {
      override val value = "user_friends"
    }

    final case object Hometown extends FacebookPermission {
      override val value = "user_hometown"
    }

    final case object Gender extends FacebookPermission {
      override val value = "user_gender"
    }

    final case object Likes extends FacebookPermission {
      override val value = "user_likes"
    }

    final case object Location extends FacebookPermission {
      override val value = "user_location"
    }

    final case object Photos extends FacebookPermission {
      override val value = "user_photos"
    }

    final case object Videos extends FacebookPermission {
      override val value = "user_videos"
    }

    final case object Status extends FacebookPermission {
      override val value = "user_status"
    }

    final case object TaggedPlaces extends FacebookPermission {
      override val value = "user_tagged_places"
    }

    final case object Posts extends FacebookPermission {
      val value = "user_posts"
    }
  }

  object EventsGroupsPages {
    val values = Set(ManagePages, PagesShowList, PublishPages)

    final case object ManagePages extends FacebookPermission {
      override val value = "manage_pages"
    }

    final case object PagesShowList extends FacebookPermission {
      override val value = "pages_show_list"
    }

    final case object PublishPages extends FacebookPermission {
      override val value = "publish_pages"
    }
  }

}
