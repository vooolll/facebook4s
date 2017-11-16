package domain.feed

import java.time.Instant

import cats.syntax.option._
import io.circe.JsonObject

final case class FacebookFeed(posts: List[FacebookSimplePost], paging: FacebookPaging)

final case class FacebookAdminCreator(id: Int, name: String)

final case class FacebookPlace(id: String)

final case class FacebookMessageTag(
  id         : String,
  name       : String,
  tagProfile : FacebookTagProfile,
  offset     : Integer,
  length     : Integer)

final case class FacebookPrivacy()

sealed trait FacebookPromotion

case object FacebookPromotionActive extends FacebookPromotion

final case class FacebookProperty(
  name : String,
  text : String,
  href : String)

sealed trait FacebookStatusType

case object FacebookAddedPhotos extends FacebookStatusType

final case class FacebookTargeting(countries: Seq[String])

sealed trait FacebookPostType

case object FacebookPhoto extends FacebookPostType

final case class FacebookSimplePost(
  id          : String,
  story       : String,
  createdTime : Instant)

final case class FacebookPost(
  id                    : String,
  story                 : String,
  createdTime           : Instant,
  adminCreator          : Option[FacebookAdminCreator] = none,
  application           : Option[FacebookApp] = none,
  callToAction          : Map[String, String] = Map.empty,
  caption               : Option[String] = none,
  description           : Option[String] = none,
  feedTargeting         : Map[String, String] = Map.empty,
  from                  : Option[FacebookProfile] = none,
  icon                  : Option[String] = none,
  instagramEligibility  : Option[String] = none,
  isHidden              : Option[Boolean] = none,
  isInstagramEligible   : Option[String] = none,
  isPublished           : Option[Boolean] = none,
  link                  : Option[String] = none,
  message               : Option[String] = none,
  messageTags           : Seq[FacebookMessageTag] = Nil,
  name                  : Option[String] = none,
  objectId              : Option[String] = none,
  parentId              : Option[String] = none,
  permalinkUrl          : Option[String] = none,
  picture               : Option[String] = none,
  place                 : Option[FacebookPlace] = none,
  privacy               : Option[FacebookPrivacy] = none,
  promotableId          : Option[String] = none,
  promotionStatus       : Option[FacebookPromotion] = none,
  properties            : Option[FacebookProperty] = none,
  shares                : Map[String, String] = Map.empty,
  source                : Option[String] = none,
  statusType            : Option[FacebookStatusType] = none,
  storyTags             : Seq[FacebookMessageTag] = Nil,
  targeting             : Option[FacebookTargeting] = none,
  to                    : Option[FacebookProfile] = none,
  postType              : Option[FacebookPostType] = none,
  updatedTime           : Option[Instant] = none,
  withTags              : Option[JsonObject] = none)


final case class FacebookPaging(
  next     : Option[String],
  previous : Option[String])