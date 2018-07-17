#### Create FacebookClient
In order to use api you need to create `FacebookClient` all api is available via this object.

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

Or using typesafe config or environment variables:

```tut:silent
import com.github.vooolll.client.FacebookClient
```

```tut:book
val facebookClient = FacebookClient()
```
In example above `FacebookClient` will use configured parameters, see [Configuration](#configuration) section.


#### Configuration
You could configure `client id`(`application id`) and `application secret` using environment variables:
```bash
export FACEBOOK_CLIENT_ID='your client id'
export FACEBOOK_APP_SECRET='your application secret'
export FACEBOOK_REDIRECT_URI='http://localhost:9000/redirect'
```
or typesafe config, typically known as `application.conf`

```scala
facebook {
  clientId = "your client id"
  redirectUri = "http://localhost:9000/redirect"
  appSecret = "your application secret"
}
```