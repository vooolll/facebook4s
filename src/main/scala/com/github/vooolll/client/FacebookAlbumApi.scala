package com.github.vooolll.client

import com.github.vooolll.client.FacebookClient._
import com.github.vooolll.services.FacebookInternals

import scala.concurrent.Future

trait FacebookAlbumApi extends FacebookInternals {

  import com.github.vooolll.serialization.FacebookDecoders.decodeAlbums

  /**
    * @param profileId profile id
    * @param accessToken user access token
    * @return FacebookAlbums
    */
  def albums(
    profileId: ProfileId
  )(implicit accessToken: AccessToken): Future[Albums] =
    sendRequestOrFail(albumsUri(profileId, accessToken))

  /**
    * @param profileId profile id
    * @param accessToken user access token
    * @return either FacebookAlbums or FacebookError
    */
  def albumsResult(
    profileId: ProfileId
  )(implicit accessToken: AccessToken): FutureResult[Albums] =
    sendRequest(albumsUri(profileId, accessToken))

}
