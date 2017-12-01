package domain.posts

import services.HasStringValue

object FacebookPostAttributes {

  val defaultPostAttributeValues = Seq(Id, Story, CreatedTime)

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

}
