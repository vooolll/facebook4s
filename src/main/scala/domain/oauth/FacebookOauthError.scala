package domain.oauth

import domain.oauth.FacebookError.FacebookErrorType

/**
  * Oauth error
  *
  * @param error facebook error message
  */
case class FacebookOauthError(error: FacebookError) extends HasFacebookError


object FacebookError {
  trait FacebookErrorType

  object Session extends FacebookErrorType
  object Unknown extends FacebookErrorType
  object ServiceDown extends FacebookErrorType
  object TooManyCalls extends FacebookErrorType
  object UserTooManyCalls extends FacebookErrorType
  object PermissionDenied extends FacebookErrorType
  object AccessTokenHasExpired extends FacebookErrorType
  object PermissionNotGrantedOrRemoved extends FacebookErrorType
  object ApplicationLimitReached extends FacebookErrorType
  object TemporarilyBlockedForPoliciesViolations extends FacebookErrorType
  object DuplicatePost extends FacebookErrorType
  object ErrorPostingLink extends FacebookErrorType
  object InvalidVerificationCodeFormat extends FacebookErrorType
  object Facebook4SInternalError extends FacebookErrorType

  val values = Set(Session, Unknown, ServiceDown, TooManyCalls, UserTooManyCalls, PermissionDenied,
    AccessTokenHasExpired, ApplicationLimitReached,
    TemporarilyBlockedForPoliciesViolations, DuplicatePost, ErrorPostingLink,
    PermissionNotGrantedOrRemoved, InvalidVerificationCodeFormat)
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