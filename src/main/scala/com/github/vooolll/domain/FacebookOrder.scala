package com.github.vooolll.domain

object FacebookOrder {
  case object Chronological extends FacebookOrder
  case object Ranked extends FacebookOrder
  case object ReverseChronological extends FacebookOrder
}

trait FacebookOrder