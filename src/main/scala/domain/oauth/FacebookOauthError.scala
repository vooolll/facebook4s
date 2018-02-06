package domain.oauth

import domain.oauth.FacebookError.FacebookErrorType

/**
  * Oauth error
  *
  * @param error facebook error message
  */
case class FacebookOauthError(error: FacebookError) extends HasFacebookError


object FacebookError {

  trait FacebookErrorType {
    val code: Int
  }

  object InvalidApiKey extends FacebookErrorType {
    val code = 101
  }

  object Session extends FacebookErrorType {
    val code = 102
  }

  object Unknown extends FacebookErrorType {
    val code = 1
  }

  object ServiceDown extends FacebookErrorType {
    val code = 2
  }

  object TooManyCalls extends FacebookErrorType {
    val code = 4
  }

  object UserTooManyCalls extends FacebookErrorType {
    val code = 17
  }

  object PermissionDenied extends FacebookErrorType {
    val code = 10
  }

  object AccessTokenHasExpired extends FacebookErrorType {
    val code = 190
  }

  object PermissionNotGrantedOrRemoved extends FacebookErrorType {
    override val code = 200
  }

  object ApplicationLimitReached extends FacebookErrorType {
    val code = 341
  }

  object Blocked extends FacebookErrorType {
    val code = 368
  }

  object DuplicatePost extends FacebookErrorType {
    val code = 506
  }

  object ErrorPostingLink extends FacebookErrorType {
    val code = 1609005
  }

  object InvalidVerificationCodeFormat extends FacebookErrorType {
    val code = 100
  }

  object Facebook4SUnsupportedError extends FacebookErrorType {
    val code = -1
  }
  object SpecifiedObjectNotFound extends FacebookErrorType {
    val code = 803
  }

  val values = Set(InvalidApiKey, Session, Unknown, ServiceDown, TooManyCalls, UserTooManyCalls, PermissionDenied,
    AccessTokenHasExpired, ApplicationLimitReached, Blocked, DuplicatePost,
    ErrorPostingLink, PermissionNotGrantedOrRemoved, InvalidVerificationCodeFormat, SpecifiedObjectNotFound)
}

/**
  * @param message Facebook error message
  */
case class FacebookError(message: String,  errorType: FacebookErrorType)

/**
  * Base trait for errors
  */
trait HasFacebookError {
  val error: FacebookError
}