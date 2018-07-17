#### Comment api
Supported `comment` fields - `id`, `from`, `created_time`, `message`, `parent`, `object`, `attachment`

Supported `summary`(optional) fields - `total_count`, `order`, `can_comment`


```tut:silent
import com.github.vooolll.client.FacebookClient
import com.github.vooolll.domain.posts._

import scala.concurrent.ExecutionContext.Implicits.global // don't do it in production environment, only for example purpose

val facebookClient = FacebookClient()

implicit val facebookAccessToken = FacebookClient.accessToken("some token value")

facebookClient.comments(FacebookPostId("post id"), summary = true).map { comments =>
  println("Comments: " + comments)
}
```