It is possible to specify `client id` and `application secret` explicitly:

```tut:silent
import com.github.vooolll.client._
import com.github.vooolll.domain.oauth._
```

```tut:book
val facebookClient = FacebookClient(FacebookClientId("your client id"), FacebookAppSecret("your app secret"))

//or

val facebookClient = FacebookClient("your client id", "your app secret")
```