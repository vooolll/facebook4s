It is possible to specify `client id` and `application secret` explicitly:

```scala
import com.github.vooolll.client._
import com.github.vooolll.domain.oauth._
```

```scala
val facebookClient = FacebookClient(FacebookClientId("your client id"), FacebookAppSecret("your app secret"))
// facebookClient: com.github.vooolll.client.FacebookClient = com.github.vooolll.client.FacebookClient@6db2a260

//or

val facebookClient = FacebookClient("your client id", "your app secret")
// <console>:13: warning: Unused import
//        import com.github.vooolll.domain.oauth._
//                                               ^
// facebookClient: com.github.vooolll.client.FacebookClient = com.github.vooolll.client.FacebookClient@59f5a376
```
