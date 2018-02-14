package client

import client.FacebookClient.{AccessToken, accessToken, _}
import domain.albums.photo.FacebookPhotoAttributes._
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
    photoId     : PhotoId,
    accessToken : AccessToken,
    fields      : Seq[PhotoAttribute]): Future[Photo] =
    sendRequestOrFail(photoUri(photoId, accessToken, fields))

  /**
    * @param photoId Id of facebook photo
    * @param accessToken User access token
    * @return future FacebookPhoto
    */
  def photo(
      photoId     : PhotoId,
      accessToken : AccessToken): Future[Photo] =
    photo(photoId, accessToken, defaultPhotoAttributeValues)

  /**
    * @param photoId Id of facebook photo
    * @param accessTokenValue String representation of user access token
    * @param fields Sequence of facebook photo attributes
    * @return FacebookPhoto
    */
  def photo(
    photoId          : PhotoId,
    accessTokenValue : String,
    fields           : Seq[PhotoAttribute]): Future[Photo] =
    photo(photoId, accessToken(accessTokenValue), fields)

  /**
    * @param photoId Id of facebook photo
    * @param accessTokenValue String representation of user access token
    * @return FacebookPhoto
    */
  def photo(
    photoId          : PhotoId,
    accessTokenValue : String): Future[Photo] =
    photo(photoId, accessTokenValue, defaultPhotoAttributeValues)

  /**
    * @param photoId Id of facebook photo
    * @param accessToken User access token
    * @param fields Sequence of facebook photo attributes
    * @return future either FacebookPhoto or FacebookError
    */
  def photoResult(
    photoId     : PhotoId,
    accessToken : AccessToken,
    fields      : Seq[PhotoAttribute]): AsyncPhotoResult =
    sendRequest(photoUri(photoId, accessToken, fields))

  /**
    * @param photoId Id of facebook photo
    * @param accessToken User access token
    * @return future either FacebookPhoto or FacebookError
    */
  def photoResult(
      photoId     : PhotoId,
      accessToken : AccessToken): AsyncPhotoResult =
    photoResult(photoId, accessToken, defaultPhotoAttributeValues)

  /**
    * @param photoId Id of facebook photo
    * @param accessTokenValue String representation of user access token
    * @param fields Sequence of facebook photo attributes
    * @return future either FacebookPhoto or FacebookError
    */
  def photoResult(
    photoId          : PhotoId,
    accessTokenValue : String,
    fields           : Seq[PhotoAttribute]): AsyncPhotoResult =
    photoResult(photoId, accessToken(accessTokenValue), fields)

  /**
    * @param photoId Id of facebook photo
    * @param accessTokenValue String representation of user access token
    * @return future either FacebookPhoto or FacebookError
    */
  def photoResult(
    photoId          : PhotoId,
    accessTokenValue : String): AsyncPhotoResult =
    photoResult(photoId, accessTokenValue, defaultPhotoAttributeValues)
}
