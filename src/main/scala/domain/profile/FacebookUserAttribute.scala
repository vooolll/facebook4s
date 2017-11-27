package domain.profile

import services.HasStringValue

object FacebookUserAttribute {
  val defaultAttributeValues = Seq(Name, Id, Picture, Locale, FirstName, LastName, Verified)

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

  case object FirstName extends FacebookUserAttribute {
    override def value = "first_name"
  }

  case object LastName extends FacebookUserAttribute {
    override def value = "last_name"
  }

  case object Verified extends FacebookUserAttribute {
    override def value = "verified"
  }
}

trait FacebookUserAttribute extends HasStringValue {
  def value: String
}
