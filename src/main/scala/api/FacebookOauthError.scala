package api

/**
  * Token error
  * @param error facebook error message
  */
case class FacebookOauthError(error: FacebookError) extends HasFacebookError

/**
  * @param message Facebook error message
  */
case class FacebookError(message: String)

/**
  * Base trait for errors
  */
trait HasFacebookError {
  val error: FacebookError
}