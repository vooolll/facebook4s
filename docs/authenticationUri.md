#### Getting authentication uri

`FacebookClient` can be used to get authentication url for client. It is starting point if you want to use api.

```scala
import com.github.vooolll.client.FacebookClient
import com.github.vooolll.domain.permission.FacebookPermissions._
```

```scala
val facebookClient = FacebookClient()
// <console>:11: warning: Unused import
//        import com.github.vooolll.domain.permission.FacebookPermissions._
//                                                                        ^
// facebookClient: com.github.vooolll.client.FacebookClient = com.github.vooolll.client.FacebookClient@1a345298

val urlWithCodeAsQueryParam = facebookClient.authUrl(Seq(UserDataPermissions.Posts))
// <console>:9: warning: Unused import
//        import com.github.vooolll.client.FacebookClient
//                                         ^
// urlWithCodeAsQueryParam: String = https://facebook.com/v3.0/dialog/oauth?client_id=1970529913214515&redirect_uri=http%3A%2F%2Flocalhost%3A9000%2Fredirect&response_type=code&scope=user_posts
```
