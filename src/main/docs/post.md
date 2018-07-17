#### Post api

Supported fields - `id`, `message`, `created_time`, `object_id`, `picture`, `from`

```tut:silent
import com.github.vooolll.client.FacebookClient
import com.github.vooolll.domain.posts._

import scala.concurrent.ExecutionContext.Implicits.global // don't do it in production environment, only for example purpose

val facebookClient = FacebookClient()

implicit val facebookAccessToken = FacebookClient.accessToken("some token value")

facebookClient.post(FacebookPostId("post id")).map { post =>
  println("Post: " + post)
}
```