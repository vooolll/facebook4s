#### Exchanging client code to access token
After `client code` received, it can be exchanged to `access token`.


```tut:silent
import com.github.vooolll.client.FacebookClient

import scala.util._
import scala.concurrent.ExecutionContext.Implicits.global // don't do it in production environment, only for example purpose
```

```tut:book
val facebookClient = FacebookClient()
val clientCode = "code from request"

facebookClient.userAccessToken(clientCode) onComplete {
 case Success(userAccessToken) => println(userAccessToken)
 case Failure(e) => println(e.getMessage)
}
```

#### Exchange short lived to long lived token

In order to access api you typically need user access token, it is often obtained by client application,
so assuming you already have one:

```tut:silent
val shortLivedAccessToken = "user access token"
```

Then you may exchange it to long lived access token, you need to create `FacebookClient` object:
```tut:silent

val facebookClient = FacebookClient()

facebookClient.extendUserAccessToken(shortLivedAccessToken) onComplete {
 case Success(longLivedToken) => println(longLivedToken)
 case Failure(e) => println(e.getMessage)
}
```

#### Exchange long-lived token to client code
It is possible to obtain client code using api call and long lived token from previous auth:
```tut:silent

val facebookClient = FacebookClient()

val longLivedTokeValue = "long-lived-token-value"

facebookClient.clientCode(longLivedTokeValue) onComplete {
 case Success(code) => println(code)
 case Failure(e) => println(e.getMessage)
}
```

After obtaining client code you can send it to client over secure channel

#### Obtaining app access token

In some cases you may need your application access token, you can get it using `appAccessToken` api call:
```tut:silent

val facebookClient = FacebookClient()

facebookClient.appAccessToken() onComplete {
 case Success(appAccessToken) => println(appAccessToken)
 case Failure(e) => println(e.getMessage)
}
```