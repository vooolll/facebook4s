#### Friends api

Supported fields - `id`, `name`, `picture`, `first_name`, `last_name`, `link`, `gender`, `hometown`, `location`, `email[if pulbic]`

Note: Summary(total friends count) and paging will be returned ass well

```tut:silent
import com.github.vooolll.client.FacebookClient
import com.github.vooolll.domain.profile._

import scala.concurrent.ExecutionContext.Implicits.global // don't do it in production environment, only for example purpose

val facebookClient = FacebookClient()

implicit val facebookAccessToken = FacebookClient.accessToken("some token value")

facebookClient.friends(FacebookUserId("499283963749541")) map(friends =>
  println(friends)
)
```