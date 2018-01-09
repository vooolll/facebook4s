package base

import io.circe.Decoder
import io.circe.parser.parse

trait JsonSerializationSupport {
  import ClientProbe._
  
  def decodeJson[T](path: String)(implicit d: Decoder[T]) = decodeStringJson(readFile(path))
  def decodeStringJson[T](value: String)(implicit d: Decoder[T]) = parse(value).flatMap(_.as[T])
}
