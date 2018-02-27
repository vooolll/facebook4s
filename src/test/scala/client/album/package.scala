package client

import java.net.URL

import domain.albums.image.FacebookImage
import domain.albums.photo.FacebookPhoto

package object album {

  implicit class FacebookPostWithoutLinks(photo: FacebookPhoto) {
    def uriWithoutQueryParams = photo.copy(images = photo.images.map(image =>
      image.copy(source = new URL(onlyResource(image).fold("CAN'T SPLIT")(e => "http://" + e)))))
  }

  private def onlyResource(image: FacebookImage) = {
    image.source.toString.takeWhile(_ != '?').split("/").lastOption
  }
}
