package domain

import cats.Show

final case class FacebookVersion(value: String) extends AnyVal

object FacebookVersionInstances {
  implicit val facebookVersionShow = Show.show[FacebookVersion](fbVersion => "v" + fbVersion.value)
}
