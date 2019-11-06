package com.github.vooolll.domain.oauth

import com.github.vooolll.domain.FacebookAttribute

/**
  * Trait that represents response type
  */
trait FacebookOauthResponseType extends FacebookAttribute {
  val value: String
}

/**
  * Represents client code
  */
case object FacebookCode extends FacebookOauthResponseType {
  override val value = "code"
}

/**
  * Represents short lived access token
  */
case object FacebookToken extends FacebookOauthResponseType {
  override val value = "token"
}

/**
  * Represents code and token at the same time
  */
case object FacebookCodeAndToken extends FacebookOauthResponseType {
  override val value = "code%20token"
}

/**
  * Represents granted_scopes
  */
case object FacebookGrantedScopes extends FacebookOauthResponseType {
  override val value = "granted_scopes"
}
