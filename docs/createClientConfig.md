#### Configuration
Add your `client id`(`application id`) and `application secret` to your environment variables:
```bash
export FACEBOOK_CLIENT_ID='your client id'
export FACEBOOK_APP_SECRET='your application secret'
export FACEBOOK_REDIRECT_URI='http://localhost:9000/redirect'
```
or
you can use typesafe config, typically known as `application.conf`

```scala
facebook {
  clientId = "your client id"
  redirectUri = "http://localhost:9000/redirect"
  appSecret = "your application secret"
}
```

#### Create FacebookClient
In order to use api you need to create `FacebookClient` all api is available via this object
```scala
import com.github.vooolll.client.FacebookClient
```

```scala
val facebookClient = FacebookClient()
// facebookClient: com.github.vooolll.client.FacebookClient = com.github.vooolll.client.FacebookClient@61aaec51
```
In example above `FacebookClient` will use configured parameters, see [Configuration](#configuration).
