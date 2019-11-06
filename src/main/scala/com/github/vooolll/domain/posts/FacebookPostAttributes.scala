package com.github.vooolll.domain.posts

import com.github.vooolll.domain.FacebookAttribute

object FacebookPostAttributes {

  val defaultPostAttributeValues =
    Set(Id, Messages, CreatedTime, ObjectId, Picture, From, Name, Attachments)

  trait FacebookPostAttribute extends FacebookAttribute

  case object Id extends FacebookPostAttribute {
    override def value = "id"
  }

  case object Messages extends FacebookPostAttribute {
    override def value = "message"
  }

  case object CreatedTime extends FacebookPostAttribute {
    override def value = "created_time"
  }

  case object ObjectId extends FacebookPostAttribute {
    override def value = "object_id"
  }

  case object Picture extends FacebookPostAttribute {
    override def value = "picture"
  }

  case object From extends FacebookPostAttribute {
    override def value = "from"
  }

  case object Name extends FacebookPostAttribute {
    override def value = "name"
  }

  case object Attachments extends FacebookPostAttribute {
    override def value = "attachments"
  }
}
