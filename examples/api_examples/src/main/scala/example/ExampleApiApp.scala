package example

import com.github.vooolll.client.FacebookClient
import com.github.vooolll.config.{FacebookConfig, FacebookConstants}
import com.github.vooolll.domain.feed._
import com.github.vooolll.domain.oauth._
import com.github.vooolll.domain.permission.FacebookPermissions.UserDataPermissions.Posts
import com.github.vooolll.domain.posts.{FacebookCreatePost, FacebookPostId}
import com.github.vooolll.domain.profile.FacebookUserId
import com.typesafe.config._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent._
import scala.util._

object ExampleApiApp extends App {
  val config = ConfigFactory.load

  val tokenStringValue = Properties.envOrNone("FACEBOOK_TEST_USER_ACCESS_TOKEN") getOrElse config.getString("facebook.testUserAccessToken")
  val facebookClient = FacebookClient()
  val userId = FacebookUserId("117656352360395")
  val postId = FacebookPostId("117656352360395_117427439049953")
  val applicationId = FacebookAppId("1970529913214515")

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

  val application2 = Await.result(facebookClient.application(applicationId), 3.seconds)
  println("Application: " + application2)
  println("------------------")

  val createdPostId = Await.result(facebookClient.createPost(FacebookCreatePost("hello facebook", FacebookClient.accessToken("EAAcAL79ZCFjMBAJN3jwLu4XDfZBJPSIs1ZAYKGgjPKBtk5vjY4ewt8D9AMYBJgpRnzQaHPQ20ZBiPjy1uF3QTGhq1E8G3BHhHNYLcPwTgw8qxfpWwr9nBZCMyy2LnrrlM2Qs59sjwLPoRUkSeYQPitZAMmA6b8m9iZCiUy2j6o1YHZCp5JGcBuBe")
  ), userId.copy(value = "106026833721513"))(FacebookClient.accessToken("EAAcAL79ZCFjMBAJN3jwLu4XDfZBJPSIs1ZAYKGgjPKBtk5vjY4ewt8D9AMYBJgpRnzQaHPQ20ZBiPjy1uF3QTGhq1E8G3BHhHNYLcPwTgw8qxfpWwr9nBZCMyy2LnrrlM2Qs59sjwLPoRUkSeYQPitZAMmA6b8m9iZCiUy2j6o1YHZCp5JGcBuBe")
  ), 3.seconds)
  println("Created post: " + createdPostId)
  println("------------------")

  val createdPost = Await.result(facebookClient.post(createdPostId), 3.seconds)
  println("Created post: " + createdPost)
  println("------------------")

  val userResult = Await.result(facebookClient.userProfileResult(userId), 3.seconds)
  userResult match {
    case Right(user) => println("Success: " + user)
    case Left(error) =>
      println("Failure: " + error)
      throw new RuntimeException(s"Failed to get user profile, $error")
  }
}