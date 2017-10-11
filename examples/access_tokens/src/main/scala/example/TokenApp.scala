package example

import api.FacebookClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}


object TokenApp extends App {
  val facebookClient = FacebookClient()

  //long lived token creation
  val longLivedToken = facebookClient.extendUserAccessToken("EAAbZCKhmWor0BAOCsAZCOAlpWvfH7g7VSZC0X5YqCV4yv9HdCx6JYHXN9Jb90gm5wKELtJdndFU7bsKlnbJweO43M4wzcjfNVQXM4fyQWmPmXCe0bWF1uCjfassz2yJZA5apaVhbwzZBpM9cc4wFPEMR8rACmnabqKQ3JZBp9vX8xPkMnqqdTmsG6mwCLZCSFROEB1yv9U5PgZDZD")// https://developers.facebook.com/tools/accesstoken/

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
