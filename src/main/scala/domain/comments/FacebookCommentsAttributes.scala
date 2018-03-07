package domain.comments

import domain.FacebookAttribute

object FacebookCommentsAttributes {

  val defaultCommentsAttributeValues = Seq(Id, Message, CreatedTime, Attachment, From)

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

}
