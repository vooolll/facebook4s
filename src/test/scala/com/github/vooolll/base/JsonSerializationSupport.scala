package com.github.vooolll.base

import io.circe.Decoder
import io.circe.parser.parse

trait JsonSerializationSupport {
  import ClientProbe._
  
  def decodeJson[T](path: String)(implicit d: Decoder[T]) = decodeStringJson(readFile(path))
  def decodeStringJson[T](value: String)(implicit d: Decoder[T]) = parse(value).flatMap(_.as[T]) match {
    case Right(value) => value
    case Left(e) => throw new RuntimeException(e.toString)
  }
}
