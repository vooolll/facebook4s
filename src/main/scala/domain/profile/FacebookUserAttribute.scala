package domain.profile

import domain.FacebookAttribute

object FacebookUserAttribute {
  val defaultAttributeValues = Seq(
    Name, Id, Picture, Locale, FirstName, LastName, Verified, Link, AgeRange, Timezone, Gender,
    Cover, UpdatedTime)

  case object Name extends FacebookUserAttribute {
    override def value = "name"
  }

  case object Id extends FacebookUserAttribute {
    override def value = "id"
  }

  case object Picture extends FacebookUserAttribute {
    override def value = "picture"
  }

  @deprecated(message="Won't be supported by facebook in 3.0", since="0.2.6")
  case object Locale extends FacebookUserAttribute {
    override def value = "locale"
  }

  case object FirstName extends FacebookUserAttribute {
    override def value = "first_name"
  }

  case object LastName extends FacebookUserAttribute {
    override def value = "last_name"
  }

  @deprecated(message="Won't be supported by facebook in 3.0", since="0.2.6")
  case object Verified extends FacebookUserAttribute {
    override def value = "verified"
  }

  case object Link extends FacebookUserAttribute {
    override def value = "link"
  }

  @deprecated(message="Won't be supported by facebook in 3.0", since="0.2.6")
  case object Timezone extends FacebookUserAttribute {
    override def value = "timezone"
  }

  case object Gender extends FacebookUserAttribute {
    override def value = "gender"
  }

  case object AgeRange extends FacebookUserAttribute {
    override def value = "age_range"
  }

  @deprecated(message="Won't be supported by facebook in 3.0", since="0.2.6")
  case object Cover extends FacebookUserAttribute {
    override def value = "cover"
  }

  @deprecated(message="Won't be supported by facebook in 3.0", since="0.2.6")
  case object UpdatedTime extends FacebookUserAttribute {
    override def value = "updated_time"
  }
}

trait FacebookUserAttribute extends FacebookAttribute {
  def value: String
}
