package com.github.vooolll.client

import com.github.vooolll.client.FacebookClient.{AccessToken, accessToken, _}
import com.github.vooolll.domain.albums.photo.FacebookPhotoAttributes._
import com.github.vooolll.services.FacebookInternals

import scala.concurrent.Future

trait FacebookPhotoApi extends FacebookInternals {

  import com.github.vooolll.serialization.FacebookDecoders.decodePhoto

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
    fields      : Seq[PhotoAttribute]): FutureResult[Photo] =
    sendRequest(photoUri(photoId, accessToken, fields))

  /**
    * @param photoId Id of facebook photo
    * @param accessToken User access token
    * @return future either FacebookPhoto or FacebookError
    */
  def photoResult(
      photoId     : PhotoId,
      accessToken : AccessToken): FutureResult[Photo] =
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
    fields           : Seq[PhotoAttribute]): FutureResult[Photo] =
    photoResult(photoId, accessToken(accessTokenValue), fields)

  /**
    * @param photoId Id of facebook photo
    * @param accessTokenValue String representation of user access token
    * @return future either FacebookPhoto or FacebookError
    */
  def photoResult(
    photoId          : PhotoId,
    accessTokenValue : String): FutureResult[Photo] =
    photoResult(photoId, accessTokenValue, defaultPhotoAttributeValues)
}
