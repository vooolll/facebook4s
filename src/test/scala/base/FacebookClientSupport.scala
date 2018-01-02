package base

import client.FacebookClient
import org.scalatest.{FutureOutcome, Matchers, fixture}

trait FacebookClientSupport extends fixture.AsyncWordSpec with Matchers {

  type FixtureParam = FacebookClient

  def withFixture(test: OneArgAsyncTest): FutureOutcome = {
    withFixture(test.toNoArgAsyncTest(FacebookClient()))
  }

}
