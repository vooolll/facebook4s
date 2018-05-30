package com.github.vooolll.base

import com.github.vooolll.client.FacebookClient
import org.scalatest.{FutureOutcome, Matchers, fixture}

trait FacebookClientSupport extends fixture.AsyncWordSpec with Matchers {

  type FixtureParam = FacebookClient

  def withFixture(test: OneArgAsyncTest): FutureOutcome = {
    withFixture(test.toNoArgAsyncTest(FacebookClient()))
  }

}
