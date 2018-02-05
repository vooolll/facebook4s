package client

import client.FacebookClient.AccessToken
import domain.albums.photo.{FacebookPhoto, FacebookPhotoId}
import domain.oauth.FacebookOauthError
import services.FacebookInternals

import scala.concurrent.Future

trait FacebookPhotoApi extends FacebookInternals {

  import serialization.FacebookDecoders.decodePhoto

  def photo(photoId: FacebookPhotoId, accessTokenRaw: String): Future[FacebookPhoto] = ???

  def photoResult(photoId: FacebookPhotoId, accessTokenRaw: String): Future[Either[FacebookOauthError, FacebookPhoto]] = ???
}
