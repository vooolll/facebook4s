package serialization

import com.typesafe.scalalogging.LazyLogging
import domain.oauth.FacebookError.FacebookErrorType
import domain.oauth._
import io.circe._
import io.circe.Decoder._

object FacebookErrorCodeDecoders extends LazyLogging {
  implicit val decodeErrorType: Decoder[FacebookErrorType] = decodeInt map {
    case 101     => FacebookError.InvalidApiKey
    case 102     => FacebookError.Session
    case 1       => FacebookError.Unknown
    case 2       => FacebookError.ServiceDown
    case 4       => FacebookError.TooManyCalls
    case 17      => FacebookError.UserTooManyCalls
    case 10      => FacebookError.PermissionDenied
    case 190     => FacebookError.AccessTokenHasExpired
    case 341     => FacebookError.ApplicationLimitReached
    case 368     => FacebookError.TemporarilyBlockedForPoliciesViolations
    case 506     => FacebookError.DuplicatePost
    case 1609005 => FacebookError.ErrorPostingLink
    case value if value >= 200 && value <= 299 => FacebookError.PermissionNotGrantedOrRemoved
    case 100     => FacebookError.InvalidVerificationCodeFormat
    case e       =>
      logger.warn("Unknown code : " + e)
      FacebookError.Facebook4SUnsupportedError
  }
}
