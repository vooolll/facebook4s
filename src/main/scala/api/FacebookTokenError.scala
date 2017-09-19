package api

case class FacebookTokenError(error: FacebookError)

case class FacebookError(message: String)