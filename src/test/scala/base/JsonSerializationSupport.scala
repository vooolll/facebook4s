package base

import io.circe.Decoder
import io.circe.parser.parse

trait JsonSerializationSupport {
  import ClientProbe._
  
  def decodeJson[T](path: String)(implicit d: Decoder[T]) = parse(readFile(path)).flatMap(_.as[T])
}
