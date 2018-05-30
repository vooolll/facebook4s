package com.github.vooolll.domain.oauth

trait FacebookApplicationId {
  val value: String
}

/**
  * Facebook client id, it is also called app id
  * @param value facebook application id
  */
final case class FacebookClientId(value: String) extends FacebookApplicationId

/**
  * Facebook app id, it is also called client id
  * @param value facebook application id(fixed size value - 16)
  */
final case class FacebookAppId(value: String) extends FacebookApplicationId