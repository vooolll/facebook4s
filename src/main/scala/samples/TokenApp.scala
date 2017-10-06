package samples

import api.FacebookClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}


object TokenApp extends App {
  val facebookClient = FacebookClient()

  //long lived token creation
  val longLivedToken = facebookClient.extendUserAccessToken("short-lived-user-access-token")// https://developers.facebook.com/tools/accesstoken/

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
