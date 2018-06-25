package example

import com.github.vooolll.client.FacebookClient
import com.github.vooolll.config.{FacebookConfig, FacebookConstants}
import com.github.vooolll.domain.feed._
import com.github.vooolll.domain.oauth._
import com.github.vooolll.domain.permission.FacebookPermissions.UserDataPermissions.Posts
import com.github.vooolll.domain.posts.FacebookPostId
import com.github.vooolll.domain.profile.FacebookUserId

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent._

object UserProfileApp extends App {
  val facebookClient = FacebookClient()

  //getting request urls
  println(facebookClient.buildAuthUrl(Seq(Posts), responseType = FacebookToken))

  val userId = FacebookUserId("499283963749541")
  val postId = FacebookPostId("499313270413277_527696260908311")
  val applicationId = FacebookAppId("1969406143275709")
  val tokenStringValue = "token"

  implicit val token = FacebookClient.accessToken(tokenStringValue)

  val user = Await.result(facebookClient.userProfile(userId), 3.seconds)
  println("User: " + user)
  println("------------------")

  val feed = Await.result(facebookClient.feed(userId), 3.seconds)
  println("Feed: + " + feed)
  println("------------------")

  val post = Await.result(facebookClient.post(postId), 3.seconds)
  println("Post: " + post)
  println("------------------")

  val application1 = Await.result(facebookClient.application(FacebookConfig.clientId), 3.seconds)
  println("Application: " + application1)
  println("------------------")

  val application2 = Await.result(facebookClient.application(applicationId), 3.seconds)
  println("Application: " + application2)
  println("------------------")

  val userResult = Await.result(facebookClient.userProfileResult(userId), 3.seconds)
  userResult match {
    case Right(user) => println("Success: " + user)
    case Left(error) => println("Failure: " + error)
  }
  
}