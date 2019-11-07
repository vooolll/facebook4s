#### User api

Supported fields - `id`, `name`, `picture`, `first_name`, `last_name`, `link`, `gender`, `age_range`, `hometown`, `location`, `email`


```scala
import com.github.vooolll.client.FacebookClient
import com.github.vooolll.domain.profile._

import scala.concurrent.ExecutionContext.Implicits.global // don't do it in production environment, only for example purpose

val facebookClient = FacebookClient()

implicit val facebookAccessToken = FacebookClient.accessToken("some token value")

facebookClient.currentUserProfile map(user =>
  println(user)
)

facebookClient.userProfile(FacebookUserId("user id")) map(user =>
  println(user)
)
```
