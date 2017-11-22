import client.FacebookClient
import config.{FacebookConfig, FacebookConstants}
import domain.feed._
import domain.oauth._
import domain.permission.FacebookPermissions.FacebookUserPosts
import domain.profile.FacebookUserId

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

object UserProfileApp extends App {
  val facebookClient = FacebookClient()

  //getting request urls
  println(facebookClient.authUrl(Seq(FacebookUserPosts), responseType = FacebookToken))

  val userId = FacebookUserId("499283963749541")
  val tokenStringValue = "token"


  //Profile
  facebookClient.userProfile(userId, tokenStringValue) map(user =>
    println(user) //FacebookUser(FacebookUserId(499283963749541),Valeryi Baibossynov)
  )

  val facebookAccessToken = FacebookAccessToken(
    TokenValue(tokenStringValue),
    UserAccessToken("barear", 1000.seconds)
  )

  facebookClient.userProfile(userId, facebookAccessToken) map(user =>
    println(user) //FacebookUser(FacebookUserId(499283963749541),Valeryi Baibossynov)
  )

  // Feed
  facebookClient.feed(userId, facebookAccessToken) map(feed =>
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

  // Application
  facebookClient.application(FacebookConfig.clientId, facebookAccessToken) map(application =>
    println("Application: " + application)) //Application: FacebookApplication(FacebookAppId(1969406143275709),https://www.facebook.com/games/?app_id=1969406143275709,testing_app)


  //or
  facebookClient.application(FacebookAppId("1969406143275709"), facebookAccessToken) map(application =>
    println("Application: " + application) //Application: FacebookApplication(FacebookAppId(1969406143275709),https://www.facebook.com/games/?app_id=1969406143275709,testing_app)
    )


  for {
    app  <- facebookClient.application(FacebookConfig.clientId, facebookAccessToken)
    user <- facebookClient.userProfile(userId, facebookAccessToken)
    feed <- facebookClient.feed(userId, facebookAccessToken)
  } {
    println(user)
    println(feed)
    println(app)
  }

  facebookClient.userProfileResult(userId, facebookAccessToken) map {
    case Right(user) => println("Success: " + user)
    case Left(error) => println("Failure: " + error)
  }
}