facebook4s
=========

[![Build Status](https://travis-ci.org/vooolll/facebook4s.svg?branch=master)](https://travis-ci.org/vooolll/facebook4s) [![codecov.io](http://codecov.io/github/vooolll/facebook4s/coverage.svg?branch=master)](http://codecov.io/github/vooolll/facebook4s?branch=master) [![License](http://img.shields.io/:license-Apache%202-red.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt) 

Prerequisites
-------------
Scala 2.12.x

SBT 0.13.x


### Installation
[Link to a header]
Add the following line to your sbt dependencies: 
```scala
"com.github.vooolll" %% "facebook4s" % "0.1.5"
```

Note: make sure that you have in your `build.sbt`
```scala
resolvers += Resolver.sonatypeRepo("releases")
```

### Usage

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
import api.FacebookClient

val facebookClient = FacebookClient()
```
In example above `FacebookClient` will use configured parameters, see [Configuration](#configuration).

If you want to specify client id and application secret explicitly you can do it as expected:
```scala
import api.FacebookClient

val facebookClient = FacebookClient("your client id", "your app secret")
```

or you can use types to your advantage:
```scala
import api.FacebookClient
import domain.{FacebookClientId, FacebookAppSecret}

val facebookClient = FacebookClient(FacebookClientId("your client id"), FacebookAppSecret("your app secret"))
```

#### Getting authentication uri

`FacebookClient` can be used to get authentication url for client. It is starting point if you want to use api.
```scala
import api.FacebookClient
import domain.permission.FacebookPermissions.FacebookUserPosts

val facebookClient = FacebookClient()

val urlWithCodeAsQueryParam = facebookClient.authUrl(Seq(FacebookUserPosts))

println(url) // prints url client needs to request to get credentials (client code by default)
```

Or you can tell to `FacebookClient` that you want to receive `access token` instead of `client code`
by passing specific argument: 

```scala
import domain.permission.FacebookPermissions.FacebookUserPosts
import domain.oauth.FacebookToken

val urlWithTokenAsQueryParam = facebookClient.authUrl(Seq(FacebookUserPosts), FacebookToken)
```

#### Exchanging client code to access token
After `client code` received, it can be exchanged to `access token`.


```scala
import api.FacebookClient
import scala.util.{Success, Failure}

val facebookClient = FacebookClient()
val clientCode = "code from request"

facebookClient.userAccessToken(clientCode) onComplete {
  case Success(token) => println(token)
  case Failure(e) => println(e.getMessage)
}
```

#### Exchange short lived to long lived token

In order to access api you typically need user access token, it is often obtained by client application,
so assuming you already have one:

```scala
val shortLivedAccessToken = "user access token"
```

Then you may exchange it to long lived access token, you need to create `FacebookClient` object:
```scala
import api.FacebookClient
import scala.util.{Success, Failure}

val facebookClient = FacebookClient()

facebookClient.extendUserAccessToken(shortLivedAccessToken) onComplete {
 case Success(longLivedToken) => println(longLivedToken)
 case Failure(e) => println(e.getMessage)
}
```

#### Exchange long-lived token to client code
It is possible to obtain client code using api call and long lived token from previous auth:
```scala
import api.FacebookClient
import scala.util.{Success, Failure}

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
```scala
import api.FacebookClient
import scala.util.{Success, Failure}

val facebookClient = FacebookClient()

facebookClient.appAccessToken() onComplete {
 case Success(appAccessToken) => println(appAccessToken)
 case Failure(e) => println(e.getMessage)
}
```

### Api references
* [FacebookClient][2]
* [FacebookAccessToken][3]
* [AppAccessToken][10]
* [UserAccessToken][11]
* [FacebookClientId][4]
* [FacebookClientCode][6]
* [FacebookRedirectUri][7]
* [FacebookAppSecret][8]
* [FacebookVersion][9]
* [FacebookOauthError][12]

### License

Facebook4s is released under the [Apache 2 License][5].

### Credits

### Authors

[1]: https://developers.facebook.com/docs/facebook-login/access-tokens/expiration-and-extension
[2]: https://vooolll.github.io/facebook4s/api/FacebookClient.html
[3]: https://vooolll.github.io/facebook4s/domain/oauth/FacebookAccessToken.html
[4]: https://vooolll.github.io/facebook4s/domain/oauth/FacebookClientId.html
[5]: http://www.apache.org/licenses/LICENSE-2.0.txt
[6]: https://vooolll.github.io/facebook4s/domain/oauth/FacebookClientCode.html
[7]: https://vooolll.github.io/facebook4s/domain/oauth/FacebookRedirectUri.html
[8]: https://vooolll.github.io/facebook4s/domain/oauth/FacebookAppSecret.html
[9]: https://vooolll.github.io/facebook4s/domain/oauth/FacebookVersion.html
[10]: https://vooolll.github.io/facebook4s/domain/oauth/AppAccessToken.html
[11]: https://vooolll.github.io/facebook4s/domain/oauth/UserAccessToken.html
[12]: https://vooolll.github.io/facebook4s/api/FacebookOauthError.html
