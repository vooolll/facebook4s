facebook4s
=========

[![Build Status](https://travis-ci.org/vooolll/facebook4s.svg?branch=master)](https://travis-ci.org/vooolll/facebook4s) [![codecov.io](http://codecov.io/github/vooolll/facebook4s/coverage.svg?branch=master)](http://codecov.io/github/vooolll/facebook4s?branch=master) [![License](http://img.shields.io/:license-Apache%202-red.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)

## Road map

### 0.2.0
Implement, test and write documentation that covers workflow described [here][1]. And make it available via sbt. 

### Usage

#### Configuration
Add your client id(application id) and application secret to your environment variables:
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
In example above `FacebookClient` will use configured parameters, see Configuration section.

If you want to specify client id and application secret explicitly you can do it as expected:
```scala
import api.FacebookClient

val facebookClient = FacebookClient("your client id", "your app secret")
```

or you can use types for your advantage:
```scala
import api.FacebookClient
import domain.{FacebookClientId, FacebookAppSecret}

val facebookClient = FacebookClient(FacebookClientId("your client id"), FacebookAppSecret("your app secret"))
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


### sbt


### License

Facebook4s is released under the [Apache 2 License][5].

### Credits

### Authors

[1]: https://developers.facebook.com/docs/facebook-login/access-tokens/expiration-and-extension
[5]: http://www.apache.org/licenses/LICENSE-2.0.txt
