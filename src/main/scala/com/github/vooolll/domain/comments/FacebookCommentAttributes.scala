package com.github.vooolll.domain.comments

import com.github.vooolll.domain.FacebookAttribute

object FacebookCommentAttributes {

  val defaultCommentAttributeValues = Seq(Id, Message, CreatedTime, Parent, From, Object)

  case object Id extends FacebookCommentAttribute {
    override def value = "id"
  }

  case object Message extends FacebookCommentAttribute {
    override def value = "message"
  }

  case object Parent extends FacebookCommentAttribute {
    override def value = "parent"
  }

  case object CreatedTime extends FacebookCommentAttribute {
    override def value = "created_time"
  }

  case object From extends FacebookCommentAttribute {
    override def value = "from"
  }

  case object Object extends FacebookCommentAttribute {
    override def value = "object"
  }

}

trait FacebookCommentAttribute extends FacebookAttribute
