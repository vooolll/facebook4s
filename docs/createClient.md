#### Create FacebookClient
In order to use api you need to create `FacebookClient` all api is available via this object.

It is possible to specify `client id` and `application secret` explicitly:

```scala
import com.github.vooolll.client._
import com.github.vooolll.domain.oauth._
```

```scala
val facebookClient = FacebookClient(FacebookClientId("your client id"), FacebookAppSecret("your app secret"))
// facebookClient: com.github.vooolll.client.FacebookClient = com.github.vooolll.client.FacebookClient@3ef1b8d3

//or

val facebookClient = FacebookClient("your client id", "your app secret")
// facebookClient: com.github.vooolll.client.FacebookClient = com.github.vooolll.client.FacebookClient@51a5b1c3
```

Or using typesafe config or environment variables:

```scala
import com.github.vooolll.client.FacebookClient
```

```scala
val facebookClient = FacebookClient()
// facebookClient: com.github.vooolll.client.FacebookClient = com.github.vooolll.client.FacebookClient@1712d998
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
