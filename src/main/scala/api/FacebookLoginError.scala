package api

case class FacebookLoginError(error: FacebookError)

case class FacebookError(message: String)