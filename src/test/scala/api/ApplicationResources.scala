package api

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

trait ApplicationResources {
  implicit lazy val system = ActorSystem()
  implicit lazy val mat = ActorMaterializer()
  implicit lazy val ec = system.dispatcher
}
