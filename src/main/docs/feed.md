#### Feed api

`FacebookFeed` includes `List[FacebookPost]`. So the same fields supported as listed in post api section.

```tut:silent
import com.github.vooolll.client.FacebookClient
import com.github.vooolll.domain.profile._

import scala.concurrent.ExecutionContext.Implicits.global // don't do it in production environment, only for example purpose

val facebookClient = FacebookClient()

implicit val facebookAccessToken = FacebookClient.accessToken("some token value")

facebookClient.feed(FacebookUserId("499283963749541")) map(feed =>
  println(feed)