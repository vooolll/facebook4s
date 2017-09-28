package api

case class FacebookTokenError(error: FacebookError) extends HasFacebookError

case class FacebookError(message: String)

trait HasFacebookError {
  val error: FacebookError
}