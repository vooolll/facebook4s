#### Either based api
In case you want to use `Result`(`Either`) based api you can use Result suffixed methods, for example if `facebookClient.userProfile`
return `Future[FacebookUser]`, then `facebookClient.userProfileResult` returns `Future[Either[FacebookError, FacebookUser]]`

Example:
```tut:silent
import com.github.vooolll.client.FacebookClient
import com.github.vooolll.domain.profile._

import scala.concurrent.ExecutionContext.Implicits.global // don't do it in production environment, only for example purpose

val facebookClient = FacebookClient()

implicit val facebookAccessToken = FacebookClient.accessToken("some token value")

facebookClient.userProfileResult(FacebookUserId("user id"), facebookAccessToken) map {
  case Right(user) => println("Success: " + user)
  case Left(error) => println("Failure: " + error)
}
```