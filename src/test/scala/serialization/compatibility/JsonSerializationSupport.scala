package serialization.compatibility

import io.circe.Decoder
import io.circe.parser.parse

trait JsonSerializationSupport {
  import client.ClientProbe._
  
  def decodeJson[T](path: String)(implicit d: Decoder[T]) = parse(readFile(path)).flatMap(json => json.as[T])
}
