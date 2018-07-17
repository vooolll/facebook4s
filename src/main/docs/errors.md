#### Error support in `Result` api

List of supported `errorType`'s of `FacebookError`
```tut:silent
import com.github.vooolll.domain.oauth._

Set(InvalidApiKey, Session, Unknown, ServiceDown, TooManyCalls, UserTooManyCalls, PermissionDenied,
  AccessTokenHasExpired, ApplicationLimitReached, Blocked, DuplicatePost,
  ErrorPostingLink, PermissionNotGrantedOrRemoved, InvalidVerificationCodeFormat, SpecifiedObjectNotFound)
```