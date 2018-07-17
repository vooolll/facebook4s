#### Albums api

Supported fields - `id`, `created_time`, `name`

```scala
import com.github.vooolll.client.FacebookClient
import com.github.vooolll.domain.profile.FacebookProfileId

import scala.concurrent.ExecutionContext.Implicits.global // don't do it in production environment, only for example purpose

val facebookClient = FacebookClient()

implicit val facebookAccessToken = FacebookClient.accessToken("some token value")

facebookClient.albums(FacebookProfileId("user id")) map { albums =>
  println("Albums: " + albums)
}
```
