package example

import api.FacebookClient
import domain.permission.FacebookPermissions.FacebookUserPosts

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}


object TokenApp extends App {
  val facebookClient = FacebookClient()


  val authUri = facebookClient.authUrl(Seq(FacebookUserPosts))

  println(authUri)

  //long lived token creation
  val longLivedToken = facebookClient.extendUserAccessToken("your token")// https://developers.facebook.com/tools/accesstoken/

  longLivedToken onComplete {
    case Success(token) =>
      println(token) // long lived token you can use it on server side, or send it to client

      //obtain client code
      val clientCode = facebookClient.clientCode(token.tokenValue.value)
      clientCode onComplete {
        case Success(code) =>
          println(code) // code can be used on client side
        case Failure(e) =>
          println(e.getLocalizedMessage)
      }
    case Failure(e) => println(e.getLocalizedMessage)
  }

}
