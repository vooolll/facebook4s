package profiling

import client.FacebookClient
import config.TestConfiguration._
import _root_.config.FacebookConfig._
import domain.posts.FacebookPostId
import domain.profile.FacebookUserId

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

object UserProfileApp extends App {

  val facebookClient = FacebookClient(clientId, appSecret)
  val realUserId = FacebookUserId("117656352360395")
  val postId = FacebookPostId("117656352360395_120118735447490")

  val profileLikes = for {
    profile <- facebookClient.userProfile(realUserId, userTokenRaw)
    likes   <- facebookClient.likes(postId, userTokenRaw)
  } yield (profile, likes)

  Await.result(profileLikes, Duration.Inf)
}
