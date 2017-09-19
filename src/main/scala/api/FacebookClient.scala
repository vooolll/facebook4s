package api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import cats.implicits._
import config.FacebookConfig.{appSecret, clientId, version, redirectUri}
import config.FacebookConstants._
import de.heikoseeberger.akkahttpplayjson.PlayJsonSupport
import domain.FacebookShowOps._
import domain.{FacebookAccessToken, _}
import org.f100ded.scalaurlbuilder.URLBuilder
import play.api.libs.json.Reads
import FacebookClient._

import scala.concurrent.Future

class FacebookClient(clientId: FacebookClientId, appSecret: FacebookAppSecret) extends PlayJsonSupport {

  import api.FacebookJsonSerializers._

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  def appAccessToken(): Future[FacebookAccessToken] = {
    appAccessTokenEither() map {
      case Right(facebookAccessToken) => facebookAccessToken
      case Left(facebookError) => throw new RuntimeException(facebookError.error.message)
    }
  }

  def appAccessTokenEither(): AppAccessTokenResult = {
    val url = URLBuilder(base = host)
      .withPathSegments(version.show, oauthUri)
      .withQueryParameters(
        "client_id"     -> clientId.show,
        "client_secret" -> appSecret.show,
        "grant_type"    -> "client_credentials"
      )

    for {
      response <- Http().singleRequest(HttpRequest(uri = url.toString()))
      accessToken <- response.status  match {
        case StatusCodes.OK                  => parse[FacebookAccessToken](response.entity).map(_ asRight)
        case StatusCodes.BadRequest          => parse[FacebookTokenError](response.entity).map(_ asLeft)
        case StatusCodes.InternalServerError => Future.successful(loginError("Internal server error.") asLeft)
        case _                               => Future.successful(loginError("Unknown exception") asLeft)
      }
    } yield accessToken
  }

  def userAccessToken(code: String): AppAccessTokenResult = {
    val url = URLBuilder(base = host)
      .withPathSegments(version.show, oauthUri)
      .withQueryParameters(
        "client_id"     -> clientId.show,
        "client_secret" -> appSecret.show,
        "redirect_uri"  -> redirectUri.show,
        "code"          -> code
      )

    for {
      response <- Http().singleRequest(HttpRequest(uri = url.toString()))
      userAccessToken <- response.status  match {
        case StatusCodes.OK                  => parse[FacebookAccessToken](response.entity).map(_ asRight)
        case StatusCodes.BadRequest          => parse[FacebookTokenError](response.entity).map(_ asLeft)
        case StatusCodes.InternalServerError => Future.successful(loginError("Internal server error.") asLeft)
        case _                               => Future.successful(loginError("Unknown exception") asLeft)
      }
    } yield userAccessToken
  }

  private def parse[T](httpEntity: HttpEntity)(implicit reads: Reads[T]) =
    Unmarshal[HttpEntity](httpEntity.withContentType(ContentTypes.`application/json`)).to[T]
}

object FacebookClient {
  def apply(clientId: FacebookClientId, appSecret: FacebookAppSecret): FacebookClient =
    new FacebookClient(clientId, appSecret)

  def apply(): FacebookClient =
    new FacebookClient(clientId, appSecret)

  def loginError(message: String) = FacebookTokenError(FacebookError(message))

  type AppAccessTokenResult = Future[Either[FacebookTokenError, FacebookAccessToken]]
}

