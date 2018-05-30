package com.github.vooolll.services

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext

trait AppResources {
  implicit val actorSystem: ActorSystem
  implicit val materializer: ActorMaterializer
  implicit val executionContext: ExecutionContext
}

class FacebookAppResources extends AppResources {
  override implicit val actorSystem: ActorSystem = ActorSystem()
  override implicit val executionContext: ExecutionContext = actorSystem.dispatcher
  override implicit val materializer: ActorMaterializer = ActorMaterializer()
}

object FacebookAppResources {
  def apply(): FacebookAppResources = new FacebookAppResources()
}