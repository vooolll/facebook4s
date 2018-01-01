import client.FacebookClient
import config.{FacebookConfig, FacebookConstants}
import domain.feed._
import domain.oauth._
import domain.permission.FacebookPermissions.FacebookUserPosts
import domain.posts.FacebookPostId
import domain.profile.FacebookUserId

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

object UserProfileApp extends App {
  val facebookClient = FacebookClient()

  //getting request urls
  println(facebookClient.buildAuthUrl(Seq(FacebookUserPosts), responseType = FacebookToken))

  val userId = FacebookUserId("499283963749541")
  val tokenStringValue = "token"


  //Profile
  facebookClient.userProfile(userId, tokenStringValue) map(user =>
    println(user) //FacebookUser(FacebookUserId(499283963749541),Some(Valeryi Baibossynov),
    // Some(FacebookUserPicture(50.0,false,https://scontent.xx.fbcdn.net/v/t1.0-1/p50x50/22728655_513792128965391_443796664145972604_n.jpg?oh=96ab05455244b5f7062d2a194e30aa8e&oe=5A88C8AD,50.0)),
    // Some(Valeryi),Some(Baibossynov),Some(https://www.facebook.com/app_scoped_user_id/499283963749541/),Some(true),Some(en_US),Some(+02:00),Some(Male),Some(AgeRange(21,None)),
    // Some(Cover(527696177574986,0.0,0.0,https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/23905322_527696177574986_8012137948429389386_n.jpg?oh=dc4f829792fa00613db226d992140957&oe=5AA288B0)),Some(2017-11-11T00:10:08Z))
    )

  val facebookAccessToken = FacebookAccessToken(
    TokenValue(tokenStringValue),
    UserAccessToken("barear", 1000.seconds)
  )

  facebookClient.userProfile(userId, facebookAccessToken) map(user =>
    println(user) //FacebookUser(FacebookUserId(499283963749541),Valeryi Baibossynov)
  )

  // Feed
  facebookClient.feed(userId, facebookAccessToken) map { feed =>
    println("Feed: + " + feed)
    println("------------------")
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
  }

  // Post
  facebookClient.post(FacebookPostId("499313270413277_527696260908311"), facebookAccessToken).map { post =>
    println("Post: " + post)
    println("------------------")
  }

  // Application
  facebookClient.application(FacebookConfig.clientId, facebookAccessToken) map { application =>
    println("Application: " + application)
    println("------------------")
  } //Application: FacebookApplication(FacebookAppId(1969406143275709),https://www.facebook.com/games/?app_id=1969406143275709,testing_app)


  //or
  facebookClient.application(FacebookAppId("1969406143275709"), facebookAccessToken) map { application =>
    println("Application: " + application) //Application: FacebookApplication(FacebookAppId(1969406143275709),https://www.facebook.com/games/?app_id=1969406143275709,testing_app)
    println("------------------")
  }


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