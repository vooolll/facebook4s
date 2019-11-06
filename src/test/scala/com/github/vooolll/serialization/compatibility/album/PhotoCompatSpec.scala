package com.github.vooolll.serialization.compatibility.album

import java.net.URL

import com.github.vooolll.base._
import com.github.vooolll.domain.albums.FacebookAlbum
import com.github.vooolll.domain.albums.image.FacebookImage
import com.github.vooolll.domain.albums.photo.{FacebookPhoto, FacebookPhotoId}
import com.github.vooolll.serialization.FacebookDecoders._
import com.github.vooolll.serialization.compatibility.CompatibilitySpec

class PhotoCompatSpec extends CompatibilitySpec {

  val imagePath = "testdata/photo_image.json"
  val albumPath = "testdata/photo_album.json"
  val photoPath = "testdata/photo.json"

  val facebookImage = FacebookImage(
    225,
    new URL(
      "https://scontent.xx.fbcdn.net/v/t1.0-0/p75x225/25396081_117607225698641_6348338142026249400_n.jpg"
    ),
    450
  )

  val facebookPhoto = FacebookPhoto(
    FacebookPhotoId("117607225698641"),
    Some(toInstant("2017-12-18T11:30:11+0000")),
    List(facebookImage),
    Some(facebookProfileAlbum)
  )

  "FacebookPhoto" should {
    s"be compatible with $imagePath" in {
      decodeJson[FacebookImage](imagePath) shouldBe facebookImage
    }
  }

  "FacebookAlbum" should {
    s"be compatible with $albumPath" in {
      decodeJson[FacebookAlbum](albumPath) shouldBe facebookProfileAlbum
    }
  }

  "FacebookPhoto" should {
    s"be compatible with $photoPath" in {
      decodeJson[FacebookPhoto](photoPath) shouldBe facebookPhoto
    }
  }

}
