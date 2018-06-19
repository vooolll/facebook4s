package com.github.vooolll.client

import com.github.vooolll.client.FacebookClient._
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
  def photo(photoId: PhotoId, fields: Seq[PhotoAttribute])(implicit accessToken: AccessToken): Future[Photo] =
    sendRequestOrFail(photoUri(photoId, accessToken, fields))

  /**
    * @param photoId Id of facebook photo
    * @param accessToken User access token
    * @return future FacebookPhoto
    */
  def photo(photoId: PhotoId)(implicit accessToken : AccessToken): Future[Photo] =
    photo(photoId, defaultPhotoAttributeValues)

  /**
    * @param photoId Id of facebook photo
    * @param accessTokenValue String representation of user access token
    * @param fields Sequence of facebook photo attributes
    * @return FacebookPhoto
    */
  @deprecated("use `AccessToken` instead of String", "0.2.9")
  def photo(photoId : PhotoId, accessTokenValue : String, fields: Seq[PhotoAttribute]): Future[Photo] =
    photo(photoId, fields)(accessToken(accessTokenValue))

  /**
    * @param photoId Id of facebook photo
    * @param accessTokenValue String representation of user access token
    * @return FacebookPhoto
    */
  @deprecated("use `AccessToken` instead of String", "0.2.9")
  def photo(photoId: PhotoId, accessTokenValue : String): Future[Photo] =
    photo(photoId, accessTokenValue, defaultPhotoAttributeValues)

  /**
    * @param photoId Id of facebook photo
    * @param accessToken User access token
    * @param fields Sequence of facebook photo attributes
    * @return future either FacebookPhoto or FacebookError
    */
  def photoResult(photoId: PhotoId, fields: Seq[PhotoAttribute])(implicit accessToken: AccessToken): FutureResult[Photo] =
    sendRequest(photoUri(photoId, accessToken, fields))

  /**
    * @param photoId Id of facebook photo
    * @param accessToken User access token
    * @return future either FacebookPhoto or FacebookError
    */
  def photoResult(photoId: PhotoId)(implicit accessToken: AccessToken): FutureResult[Photo] =
    photoResult(photoId, defaultPhotoAttributeValues)

  /**
    * @param photoId Id of facebook photo
    * @param accessTokenValue String representation of user access token
    * @param fields Sequence of facebook photo attributes
    * @return future either FacebookPhoto or FacebookError
    */
  @deprecated("use `AccessToken` instead of String", "0.2.9")
  def photoResult(photoId: PhotoId, accessTokenValue: String, fields: Seq[PhotoAttribute]): FutureResult[Photo] =
    photoResult(photoId, fields)(accessToken(accessTokenValue))

  /**
    * @param photoId Id of facebook photo
    * @param accessTokenValue String representation of user access token
    * @return future either FacebookPhoto or FacebookError
    */
  @deprecated("use `AccessToken` instead of String", "0.2.9")
  def photoResult(photoId: PhotoId, accessTokenValue: String): FutureResult[Photo] =
    photoResult(photoId, accessTokenValue, defaultPhotoAttributeValues)
}
