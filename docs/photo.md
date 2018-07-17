#### Photo api

Supported fields - `id`, `created_time`, `images`, `album`

```scala
import com.github.vooolll.client.FacebookClient
import com.github.vooolll.domain.albums.photo._

import scala.concurrent.ExecutionContext.Implicits.global // don't do it in production environment, only for example purpose

val facebookClient = FacebookClient()

implicit val facebookAccessToken = FacebookClient.accessToken("some token value")

facebookClient.photo(FacebookPhotoId("some photo id")) map { photo =>
  println("Photo: " + photo)
}
```
