package com.github.vooolll.domain.albums.photo

import com.github.vooolll.domain.FacebookAttribute

object FacebookPhotoAttributes {

  val defaultPhotoAttributeValues = Seq(Id, CreatedTime, Images, Album)

  trait FacebookPhotoAttribute extends FacebookAttribute

  case object Id extends FacebookPhotoAttribute {
    val value = "id"
  }
  case object Images extends FacebookPhotoAttribute {
    override val value = "images"
  }
  case object Album extends FacebookPhotoAttribute {
    override val value = "album"
  }
  case object CreatedTime extends FacebookPhotoAttribute {
    override val value = "created_time"
  }
}
