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

  @Deprecated("Deprecated by facebook wont be returned since 3.0")
  case object Locale extends FacebookUserAttribute {
    override def value = "locale"
  }

  case object FirstName extends FacebookUserAttribute {
    override def value = "first_name"
  }

  case object LastName extends FacebookUserAttribute {
    override def value = "last_name"
  }

  @Deprecated("Deprecated by facebook wont be returned since 3.0")
  case object Verified extends FacebookUserAttribute {
    override def value = "verified"
  }

  case object Link extends FacebookUserAttribute {
    override def value = "link"
  }

  @Deprecated("Deprecated by facebook wont be returned since 3.0")
  case object Timezone extends FacebookUserAttribute {
    override def value = "timezone"
  }

  case object Gender extends FacebookUserAttribute {
    override def value = "gender"
  }

  case object AgeRange extends FacebookUserAttribute {
    override def value = "age_range"
  }

  @Deprecated("Deprecated by facebook wont be returned since 3.0")
  case object Cover extends FacebookUserAttribute {
    override def value = "cover"
  }

  @Deprecated("Deprecated by facebook wont be returned since 3.0")
  case object UpdatedTime extends FacebookUserAttribute {
    override def value = "updated_time"
  }
}

trait FacebookUserAttribute extends FacebookAttribute {
  def value: String
}
