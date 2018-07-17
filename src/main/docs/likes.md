#### Like api
Supported `like` fields - `id`, `name`

Supported `summary`(optional) fields - `total_count`, `can_like`, `has_liked`

```tut:silent
import com.github.vooolll.client.FacebookClient
import com.github.vooolll.domain.posts._

import scala.concurrent.ExecutionContext.Implicits.global // don't do it in production environment, only for example purpose

val facebookClient = FacebookClient()

implicit val facebookAccessToken = FacebookClient.accessToken("some token value")

facebookClient.likes(FacebookPostId("post id"), summary = true).map { likes =>
  println("Like: " + likes)
}
```