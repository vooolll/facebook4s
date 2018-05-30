package com.github.vooolll.client

import java.net.URL

import com.github.vooolll.base._
import com.github.vooolll.domain.FacebookPaging
import com.github.vooolll.domain.albums.{FacebookAlbum, FacebookAlbumId, FacebookAlbums}
import com.github.vooolll.domain.albums.image.FacebookImage
import com.github.vooolll.domain.albums.photo.{FacebookPhoto, FacebookPhotoId}
import com.github.vooolll.domain.profile.FacebookProfileId

package object album {

  implicit class FacebookPhotoWithoutLinks(photo: FacebookPhoto) {
    def uriWithoutQueryParams = photo.copy(images = photo.images.map(image =>
      image.copy(source = new URL(onlyResource(image).fold("CAN'T SPLIT")(e => "http://" + e)))))
  }

  private[this] def onlyResource(image: FacebookImage) = {
    image.source.toString.takeWhile(_ != '?').split("/").lastOption
  }

  val photoId = FacebookPhotoId("120118675447496")

  val source = new URL("http://25398995_120118675447496_5830741756468130361_n.jpg")

  val profileId = FacebookProfileId("117656352360395")

  val coverAlbum = FacebookAlbum(FacebookAlbumId("120118722114158"), "Cover Photos", toInstant("2017-12-19T14:08:44+0000"))
  val profileAlbum = FacebookAlbum(FacebookAlbumId("117607235698640"), "Profile Pictures",
    toInstant("2017-12-18T11:30:10+0000"))

  val facebookAlbums = FacebookAlbums(
    List(coverAlbum, profileAlbum),
    Some(FacebookPaging(
      Some("MTIwMTE4NzIyMTE0MTU4"),
      Some("MTE3NjA3MjM1Njk4NjQw"))))

  val photo = FacebookPhoto(
    photoId,
    Some(toInstant("2017-12-19T14:08:45+0000")),
    List(FacebookImage(400, source, 800), FacebookImage(320, source, 640), FacebookImage(130, source, 260),
      FacebookImage(225, source, 450)),
    Some(coverAlbum)
  )
}
