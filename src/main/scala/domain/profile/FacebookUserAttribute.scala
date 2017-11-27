package domain.profile

import services.HasStringValue

object FacebookUserAttribute {
  val defaultAttributeValues = Seq(Name, Id, Picture)

  case object Name extends FacebookUserAttribute {
    override def value = "name"
  }

  case object Id extends FacebookUserAttribute {
    override def value = "id"
  }

  case object Picture extends FacebookUserAttribute {
    override def value = "picture"
  }
}

trait FacebookUserAttribute extends HasStringValue {
  def value: String
}
