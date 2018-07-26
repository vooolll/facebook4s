package com.github.vooolll.domain.comments

import com.github.vooolll.domain.FacebookAttribute

object FacebookCommentsAttributes {

  val defaultCommentsAttributeValues = Set(Id, Message, CreatedTime, Attachment, From, Object)

  trait FacebookCommentsAttribute extends FacebookAttribute

  case object Id extends FacebookCommentsAttribute {
    override def value = "id"
  }

  case object Message extends FacebookCommentsAttribute {
    override def value = "message"
  }

  case object Attachment extends FacebookCommentsAttribute {
    override def value = "attachment"
  }

  case object CreatedTime extends FacebookCommentsAttribute {
    override def value = "created_time"
  }

  case object From extends FacebookCommentsAttribute {
    override def value = "from"
  }

  case object Object extends FacebookCommentsAttribute {
    override def value = "object"
  }

}
