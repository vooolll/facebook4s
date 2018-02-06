package client

import client.FacebookClient.{AccessToken, accessToken}
import domain.albums.photo.FacebookPhotoAttributes._
import domain.albums.photo.{FacebookPhoto, FacebookPhotoId}
import domain.oauth.FacebookOauthError
import services.FacebookInternals

import scala.concurrent.Future

trait FacebookPhotoApi extends FacebookInternals {

  import serialization.FacebookDecoders.decodePhoto

  /**
    * @param photoId Id of facebook photo
    * @param accessToken User access token
    * @param fields Sequence of facebook photo attributes
    * @return future FacebookPhoto
    */
  def photo(
    photoId     : FacebookPhotoId,
    accessToken : AccessToken,
    fields      : Seq[FacebookPhotoAttribute]): Future[FacebookPhoto] =
    sendRequestOrFail(photoUri(photoId, accessToken, fields))

  /**
    * @param photoId Id of facebook photo
    * @param accessToken User access token
    * @return future FacebookPhoto
    */
  def photo(
      photoId     : FacebookPhotoId,
      accessToken : AccessToken): Future[FacebookPhoto] =
    photo(photoId, accessToken, defaultPhotoAttributeValues)

  /**
    * @param photoId Id of facebook photo
    * @param accessTokenValue String representation of user access token
    * @param fields Sequence of facebook photo attributes
    * @return FacebookPhoto
    */
  def photo(
    photoId          : FacebookPhotoId,
    accessTokenValue : String,
    fields           : Seq[FacebookPhotoAttribute]): Future[FacebookPhoto] =
    photo(photoId, accessToken(accessTokenValue), fields)

  /**
    * @param photoId Id of facebook photo
    * @param accessTokenValue String representation of user access token
    * @return FacebookPhoto
    */
  def photo(
    photoId          : FacebookPhotoId,
    accessTokenValue : String): Future[FacebookPhoto] =
    photo(photoId, accessTokenValue, defaultPhotoAttributeValues)

  /**
    * @param photoId Id of facebook photo
    * @param accessToken User access token
    * @param fields Sequence of facebook photo attributes
    * @return future either FacebookPhoto or FacebookOauthError
    */
  def photoResult(
    photoId     : FacebookPhotoId,
    accessToken : AccessToken,
    fields      : Seq[FacebookPhotoAttribute]): Future[Either[FacebookOauthError, FacebookPhoto]] =
    sendRequest(photoUri(photoId, accessToken, fields))

  /**
    * @param photoId Id of facebook photo
    * @param accessToken User access token
    * @return future either FacebookPhoto or FacebookOauthError
    */
  def photoResult(
      photoId     : FacebookPhotoId,
      accessToken : AccessToken): Future[Either[FacebookOauthError, FacebookPhoto]] =
    photoResult(photoId, accessToken, defaultPhotoAttributeValues)

  /**
    * @param photoId Id of facebook photo
    * @param accessTokenValue String representation of user access token
    * @param fields Sequence of facebook photo attributes
    * @return future either FacebookPhoto or FacebookOauthError
    */
  def photoResult(
    photoId          : FacebookPhotoId,
    accessTokenValue : String,
    fields           : Seq[FacebookPhotoAttribute]): Future[Either[FacebookOauthError, FacebookPhoto]] =
    photoResult(photoId, accessToken(accessTokenValue), fields)

  /**
    * @param photoId Id of facebook photo
    * @param accessTokenValue String representation of user access token
    * @return future either FacebookPhoto or FacebookOauthError
    */
  def photoResult(
    photoId          : FacebookPhotoId,
    accessTokenValue : String): Future[Either[FacebookOauthError, FacebookPhoto]] =
    photoResult(photoId, accessTokenValue, defaultPhotoAttributeValues)
}
