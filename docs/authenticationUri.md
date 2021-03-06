#### Getting authentication uri

`FacebookClient` can be used to get authentication url for client. It is starting point if you want to use api.

```scala
import com.github.vooolll.client.FacebookClient
import com.github.vooolll.domain.permission.FacebookPermissions._
import com.github.vooolll.domain.oauth._
```

```scala
val facebookClient = FacebookClient()
// facebookClient: com.github.vooolll.client.FacebookClient = com.github.vooolll.client.FacebookClient@4bd52ca4

val urlWithCodeAsQueryParam = facebookClient.authUrl(Set(UserDataPermissions.Posts))
// urlWithCodeAsQueryParam: String = https://facebook.com/v5.0/dialog/oauth?client_id=1970529913214515&redirect_uri=http%3A%2F%2Flocalhost%3A9000%2Fredirect&response_type=code&scope=user_posts

val urlWithTokenAsQueryParam = facebookClient.authUrl(Set(UserDataPermissions.Posts), FacebookToken)
// urlWithTokenAsQueryParam: String = https://facebook.com/v5.0/dialog/oauth?client_id=1970529913214515&redirect_uri=http%3A%2F%2Flocalhost%3A9000%2Fredirect&response_type=token&scope=user_posts
```
