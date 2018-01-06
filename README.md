facebook4s
=========

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/0c7d5adedcba4774988ad1fe3e075558)](https://www.codacy.com/app/baibossynov-valery/facebook4s?utm_source=github.com&utm_medium=referral&utm_content=vooolll/facebook4s&utm_campaign=badger)
[![Build Status](https://travis-ci.org/vooolll/facebook4s.svg?branch=master)](https://travis-ci.org/vooolll/facebook4s) [![codecov.io](http://codecov.io/github/vooolll/facebook4s/coverage.svg?branch=master)](http://codecov.io/github/vooolll/facebook4s?branch=master) [![License](http://img.shields.io/:license-Apache%202-red.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt) [![Join the chat at https://gitter.im/facebook4s](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/facebook4s/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Prerequisites
-------------
* Scala 2.12.x
* SBT 0.13.x

### Installation
Add the following line to your sbt dependencies: 
```scala
"com.github.vooolll" %% "facebook4s" % "0.2.2"
```

Note: make sure that you have in your `build.sbt`
```scala
resolvers += Resolver.sonatypeRepo("releases")
```

### Useful links
* [Examples](https://github.com/vooolll/facebook4s/tree/master/examples)
* [Documentation (Scaladoc)](https://www.javadoc.io/doc/com.github.vooolll/facebook4s_2.12/)


### User guide
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


#### Feed api

```scala
import domain.feed._
import domain.profile._
import domain.oauth._
import client.FacebookClient
import scala.concurrent.ExecutionContext.Implicits.global
  
val facebookClient = FacebookClient()
  
val facebookAccessToken: FacebookAccessToken = ??? // your token
// Feed
facebookClient.feed(FacebookUserId("499283963749541"), facebookAccessToken) map(feed =>
  println(feed)
//  prints:
//  FacebookFeed(
//    List(
//      FacebookSimplePost("499313270413277_504668796544391", "Valeryi Baibossynov updated his profile picture.",
//        toInstant("2017-10-01T13:43:05+0000")),
//      FacebookSimplePost("499313270413277_139299253081349",
//        "Valeryi Baibossynov added a life event from May 2, 1993: Born on May 2, 1993.",
//        toInstant("1993-05-02T07:00:00+0000"))
//    ),
//    FacebookPaging("https://graph.facebook.com1", "https://graph.facebook.com"))
)
  
  // or you can use raw string
  
facebookClient.feed(FacebookUserId("499283963749541"), "your user access token") map(feed =>
  println(feed))
``` 

#### Post api

Supported fields - `id`, `story`, `created_time`, `object_id`, `picture`, `from`


```scala
facebookClient.post(FacebookPostId("499313270413277_527696260908311"), facebookAccessToken).map { post =>
  println("Post: " + post)
}
```

#### Like api
Supported `like` fields - `id`, `name`

Supported `summary`(optional) fields - `total_count`, `can_like`, `has_liked`
```scala
facebookClient.likes(FacebookPostId("499313270413277_527696260908311"), facebookAccessToken, summary = true).map { likes =>
  println("Like: " + likes)
}
```

#### Post api
Supported `comment` fields - `id`, `from`, `created_time`, `message`

Supported `summary`(optional) fields - `total_count`, `order`, `can_comment`
```scala
facebookClient.comments(FacebookPostId("499313270413277_527696260908311"), facebookAccessToken, summary = true).map { comments =>
  println("Comments: " + comments)
}
```


#### User api

Supported fields - `id`, `name`, `picture`, `locale`, `first_name`, `last_name`, `verified`, `link`, `timezone`, `gender`,
    `age_range`, `cover`, `updated_time`
    
    
```scala
facebookClient.userProfile(FacebookUserId("499283963749541"), facebookAccessToken) map(user =>
  println(user) //FacebookUser(FacebookUserId(499283963749541),Some(Valeryi Baibossynov),
                  // Some(FacebookUserPicture(50.0,false,https://scontent.xx.fbcdn.net/v/t1.0-1/p50x50/22728655_513792128965391_443796664145972604_n.jpg?oh=96ab05455244b5f7062d2a194e30aa8e&oe=5A88C8AD,50.0)),
                  // Some(Valeryi),Some(Baibossynov),Some(https://www.facebook.com/app_scoped_user_id/499283963749541/),Some(true),Some(en_US),Some(+02:00),Some(Male),Some(AgeRange(21,None)),
                  // Some(Cover(527696177574986,0.0,0.0,https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/23905322_527696177574986_8012137948429389386_n.jpg?oh=dc4f829792fa00613db226d992140957&oe=5AA288B0)),Some(2017-11-11T00:10:08Z))
)
```

```scala

//raw string value supported as well
facebookClient.userProfile(FacebookUserId("499283963749541"), "your user access token") map(user =>
  println(user) //FacebookUser(FacebookUserId(499283963749541),Some(Valeryi Baibossynov),
                // Some(FacebookUserPicture(50.0,false,https://scontent.xx.fbcdn.net/v/t1.0-1/p50x50/22728655_513792128965391_443796664145972604_n.jpg?oh=96ab05455244b5f7062d2a194e30aa8e&oe=5A88C8AD,50.0)),
                // Some(Valeryi),Some(Baibossynov),Some(https://www.facebook.com/app_scoped_user_id/499283963749541/),Some(true),Some(en_US),Some(+02:00),Some(Male),Some(AgeRange(21,None)),
                // Some(Cover(527696177574986,0.0,0.0,https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/23905322_527696177574986_8012137948429389386_n.jpg?oh=dc4f829792fa00613db226d992140957&oe=5AA288B0)),Some(2017-11-11T00:10:08Z))
)
```

#### Application api

```scala
facebookClient.application(FacebookConfig.clientId, facebookAccessToken) map(application =>
  println("Application: " + application)) //Application: FacebookApplication(FacebookAppId(1969406143275709),https://www.facebook.com/games/?app_id=1969406143275709,testing_app)
 

//or
facebookClient.application(FacebookAppId("1969406143275709"), facebookAccessToken) map(application =>
  println("Application: " + application) //Application: FacebookApplication(FacebookAppId(1969406143275709),https://www.facebook.com/games/?app_id=1969406143275709,testing_app)
)
```

Note: in terms of facebook4s there is no difference between `client_id` and `application_id`. 

#### Chaining futures

You can compose facebook4s calls as expected:

```scala
for {
  app  <- facebookClient.application(FacebookConfig.clientId, facebookAccessToken)
  user <- facebookClient.userProfile(userId, facebookAccessToken)
  feed <- facebookClient.feed(userId, facebookAccessToken)
} {
  println(user)
  println(feed)
  println(app)
}
```


#### Either based api
In case you want to use `Either` based api you can use Result suffixed methods, for example if `facebookClient.userProfile`
return `Future[FacebookUser]`, then `facebookClient.userProfileResult` returns `Future[Either[FacebookOauthError, FacebookUser]]`

Example:
```scala
facebookClient.userProfileResult(userId, facebookAccessToken) map {
  case Right(user) => println("Success: " + user)
  case Left(error) => println("Failure: " + error)
}
```

### Release process
* Update `build.sbt` with new version
* Enter `sbt` shell
* Run `publishSigned`
* Run `sonatypeRelease`

### License

Facebook4s is released under the [Apache 2 License][5].


[5]: http://www.apache.org/licenses/LICENSE-2.0.txt
