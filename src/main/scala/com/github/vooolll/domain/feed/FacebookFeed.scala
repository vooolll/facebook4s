package com.github.vooolll.domain.feed

import java.net.URL

import com.github.vooolll.domain.posts.FacebookPost

final case class FacebookFeed(posts: List[FacebookPost], paging: FacebookFeedPaging)

final case class FacebookFeedPaging(next: Option[URL], previous: Option[URL])
