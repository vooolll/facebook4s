package com.github.vooolll.base

import org.scalatest.mockito.MockitoSugar
import org.scalatest.{AsyncWordSpec, Matchers}

trait AsyncSpec extends AsyncWordSpec with Matchers with MockitoSugar
