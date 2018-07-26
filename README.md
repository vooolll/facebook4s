facebook4s
=========

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/0c7d5adedcba4774988ad1fe3e075558)](https://www.codacy.com/app/baibossynov-valery/facebook4s?utm_source=github.com&utm_medium=referral&utm_content=vooolll/facebook4s&utm_campaign=badger)
[![Build Status](https://travis-ci.org/vooolll/facebook4s.svg?branch=master)](https://travis-ci.org/vooolll/facebook4s) [![codecov.io](http://codecov.io/github/vooolll/facebook4s/coverage.svg?branch=master)](http://codecov.io/github/vooolll/facebook4s?branch=master) [![License](http://img.shields.io/:license-Apache%202-red.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt) [![Join the chat at https://gitter.im/facebook4s](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/facebook4s/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

Prerequisites
-------------
* Scala 2.11.x and 2.12.x

### Installation
Add the following line to your sbt dependencies:
```scala
"com.github.vooolll" %% "facebook4s" % "0.3.0"
```

Note: make sure that you have in your `build.sbt`
```scala
resolvers += Resolver.sonatypeRepo("releases")
```

### Useful links
* [Examples](https://github.com/vooolll/facebook4s/tree/master/examples)
* [Documentation (ScalaDoc)](https://www.javadoc.io/doc/com.github.vooolll/facebook4s_2.12/)


### User guide

* [Client configuration](https://github.com/vooolll/facebook4s/tree/master/docs/createClient.md)
* [Getting authentication uri](https://github.com/vooolll/facebook4s/tree/master/docs/authenticationUri.md)
* [Token manipulations](https://github.com/vooolll/facebook4s/tree/master/docs/tokenManipulations.md)
* [Posts](https://github.com/vooolll/facebook4s/tree/master/docs/post.md)
* [Likes](https://github.com/vooolll/facebook4s/tree/master/docs/likes.md)
* [Feed](https://github.com/vooolll/facebook4s/tree/master/docs/feed.md)
* [Friends](https://github.com/vooolll/facebook4s/tree/master/docs/friends.md)
* [Comment](https://github.com/vooolll/facebook4s/tree/master/docs/comments.md)
* [User](https://github.com/vooolll/facebook4s/tree/master/docs/user.md)
* [Photo](https://github.com/vooolll/facebook4s/tree/master/docs/photo.md)
* [Albums](https://github.com/vooolll/facebook4s/tree/master/docs/albums.md)
* [Application](https://github.com/vooolll/facebook4s/tree/master/docs/application.md)
* [Either based api](https://github.com/vooolll/facebook4s/tree/master/docs/either.md)
* [Errors](https://github.com/vooolll/facebook4s/tree/master/docs/errors.md)


### Release process
* Update `build.sbt` with new version
* Enter `sbt` shell
* Run `+ publishSigned`
* Run `+ sonatypeRelease`

### License

Facebook4s is released under the [Apache 2 License][5].


[5]: http://www.apache.org/licenses/LICENSE-2.0.txt
