package client

import domain.albums.photo.FacebookPhoto

package object album {

  implicit class FacebookPostWithoutLinks(photo: FacebookPhoto) {
    def uriWithoutQueryParams = photo.copy(images = photo.images.map(image =>
      image.copy(source = image.source.takeWhile(_ != '?').split("/").lastOption getOrElse "CAN'T SPLIT")))
  }
}
