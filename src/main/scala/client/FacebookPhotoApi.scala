package client

import client.FacebookClient.accessToken
import domain.albums.photo.FacebookPhotoAttributes._
import domain.albums.photo.{FacebookPhoto, FacebookPhotoId}
import domain.oauth.FacebookOauthError
import services.FacebookInternals

import scala.concurrent.Future

trait FacebookPhotoApi extends FacebookInternals {

  import serialization.FacebookDecoders.decodePhoto

  def photo(
    photoId          : FacebookPhotoId,
    accessTokenValue : String,
    fields           : Seq[FacebookPhotoAttribute]): Future[FacebookPhoto] =
    sendRequestOrFail(photoUri(photoId, accessToken(accessTokenValue), fields))

  def photo(
    photoId          : FacebookPhotoId,
    accessTokenValue : String): Future[FacebookPhoto] =
    photo(photoId, accessTokenValue, defaultPhotoAttributeValues)

  def photoResult(
    photoId          : FacebookPhotoId,
    accessTokenValue : String,
    fields           : Seq[FacebookPhotoAttribute]): Future[Either[FacebookOauthError, FacebookPhoto]] =
    sendRequest(photoUri(photoId, accessToken(accessTokenValue), fields))

  def photoResult(
    photoId          : FacebookPhotoId,
    accessTokenValue : String): Future[Either[FacebookOauthError, FacebookPhoto]] =
    photoResult(photoId, accessTokenValue, defaultPhotoAttributeValues)
}
