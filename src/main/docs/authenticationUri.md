#### Getting authentication uri

`FacebookClient` can be used to get authentication url for client. It is starting point if you want to use api.

```tut:silent
import com.github.vooolll.client.FacebookClient
import com.github.vooolll.domain.permission.FacebookPermissions._
import com.github.vooolll.domain.oauth._
```

```tut:book
val facebookClient = FacebookClient()

val urlWithCodeAsQueryParam = facebookClient.authUrl(Seq(UserDataPermissions.Posts))

val urlWithTokenAsQueryParam = facebookClient.authUrl(Seq(UserDataPermissions.Posts), FacebookToken)
```
