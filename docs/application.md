#### Application api

```scala
import com.github.vooolll.client.FacebookClient
import com.github.vooolll.domain.oauth.FacebookAppId
import com.github.vooolll.config.FacebookConfig

import scala.concurrent.ExecutionContext.Implicits.global // don't do it in production environment, only for example purpose

val facebookClient = FacebookClient()

implicit val facebookAccessToken = FacebookClient.accessToken("some token value")

facebookClient.application(FacebookConfig.clientId) map(application =>
  println("Application: " + application))

//or
facebookClient.application(FacebookAppId("app id")) map(application =>
  println("Application: " + application))
```

Note: in terms of facebook4s there is no difference between `client id` and `application id`.
