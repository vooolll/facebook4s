package domain.posts

import domain.HasStringValue

object FacebookPostAttributes {

  val defaultPostAttributeValues = Seq(Id, Story, CreatedTime, ObjectId, Picture, From)

  trait FacebookPostAttribute extends HasStringValue

  case object Id extends FacebookPostAttribute {
    override def value = "id"
  }

  case object Story extends FacebookPostAttribute {
    override def value = "story"
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
}
