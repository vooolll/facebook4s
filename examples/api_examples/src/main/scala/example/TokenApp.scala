package example

import com.github.vooolll.client.FacebookClient
import com.github.vooolll.domain.oauth.{FacebookCode, FacebookToken}
import com.github.vooolll.domain.permission.FacebookPermissions.UserDataPermissions.Posts

import com.typesafe.config._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util._

object TokenApp extends App {
  val config = ConfigFactory.load

  val facebookClient = FacebookClient()

  //getting request urls
  val authUriToGetCode = facebookClient.buildAuthUrl(Set(Posts))
  val authUriToGetToken =
    facebookClient.buildAuthUrl(Set(Posts), responseType = FacebookToken)

  println(authUriToGetCode)
  println(authUriToGetToken)

  val shortLivingToken = Properties.envOrNone("FACEBOOK_TEST_USER_ACCESS_TOKEN") getOrElse config
    .getString("facebook.testUserAccessToken")

  //long lived token creation
  val longLivedToken = facebookClient.extendUserAccessToken(shortLivingToken) // https://developers.facebook.com/tools/accesstoken/

  longLivedToken onComplete {
    case Success(token) =>
      println(token) // long lived token you can use it on server side, or send it to client
      val clientCode = facebookClient.clientCode(token.tokenValue.value)
      clientCode onComplete {
        case Success(code) => println(code) // code can be used on client side
        case Failure(e)    => println(e.getLocalizedMessage)
      }
    case Failure(e) => println(e.getLocalizedMessage)
  }

}
