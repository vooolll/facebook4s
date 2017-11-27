package domain.profile

import services.HasStringValue

object FacebookUserAttribute {
  val defaultAttributeValues = Seq(Name, Id, Picture, Locale)

  case object Name extends FacebookUserAttribute {
    override def value = "name"
  }

  case object Id extends FacebookUserAttribute {
    override def value = "id"
  }

  case object Picture extends FacebookUserAttribute {
    override def value = "picture"
  }

  case object Locale extends FacebookUserAttribute {
    override def value = "locale"
  }
}

trait FacebookUserAttribute extends HasStringValue {
  def value: String
}
