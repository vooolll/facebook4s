package client

import client.FacebookClient._
import services.FacebookInternals

import scala.concurrent.Future

trait FacebookAlbumApi extends FacebookInternals {

  import serialization.FacebookDecoders.decodeAlbums

  /**
    * @param profileId profile id
    * @param accessTokenValue user access token string value
    * @return FacebookAlbums
    */
  def albums(profileId: ProfileId, accessTokenValue: String): Future[Albums] =
    albums(profileId, accessToken(accessTokenValue))

  /**
    * @param profileId profile id
    * @param accessToken user access token
    * @return FacebookAlbums
    */
  def albums(profileId: ProfileId, accessToken: AccessToken): Future[Albums] =
    sendRequestOrFail(albumsUri(profileId, accessToken))

  /**
    * @param profileId profile id
    * @param accessToken user access token
    * @return either FacebookAlbums or FacebookError
    */
  def albumsResult(profileId: ProfileId, accessToken: AccessToken): AsyncAlbumsResult =
    sendRequest(albumsUri(profileId, accessToken))

  /**
    * @param profileId profile id
    * @param accessTokenValue user access token value
    * @return either FacebookAlbums or FacebookError
    */
  def albumsResult(profileId: ProfileId, accessTokenValue: String): AsyncAlbumsResult =
    albumsResult(profileId, accessToken(accessTokenValue))

}
