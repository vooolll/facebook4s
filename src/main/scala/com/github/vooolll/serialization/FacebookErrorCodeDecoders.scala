package com.github.vooolll.serialization

import com.typesafe.scalalogging.LazyLogging
import com.github.vooolll.domain.oauth.FacebookError.FacebookErrorType
import com.github.vooolll.domain.oauth._
import io.circe._
import io.circe.Decoder._
import FacebookError._

object FacebookErrorCodeDecoders extends LazyLogging {
  implicit val decodeErrorType: Decoder[FacebookErrorType] = decodeInt map {
    case InvalidApiKey.code                    => InvalidApiKey
    case Session.code                          => Session
    case Unknown.code                          => Unknown
    case ServiceDown.code                      => ServiceDown
    case TooManyCalls.code                     => TooManyCalls
    case UserTooManyCalls.code                 => FacebookError.UserTooManyCalls
    case PermissionDenied.code                 => PermissionDenied
    case AccessTokenHasExpired.code            => AccessTokenHasExpired
    case ApplicationLimitReached.code          => ApplicationLimitReached
    case Blocked.code                          => Blocked
    case DuplicatePost.code                    => DuplicatePost
    case ErrorPostingLink.code                 => ErrorPostingLink
    case value if value >= 200 && value <= 299 => PermissionNotGrantedOrRemoved
    case InvalidVerificationCodeFormat.code    => InvalidVerificationCodeFormat
    case SpecifiedObjectNotFound.code          => SpecifiedObjectNotFound
    case e       =>
      logger.warn("Unknown code : " + e)
      FacebookError.Facebook4SUnsupportedError
  }
}
