/*
Copyright 2016 ScalABM

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package org.economicsl.agora.markets.auctions.mutable.continuous

import org.economicsl.agora.RandomOrderGenerator
import org.economicsl.agora.markets.tradables.TestTradable
import org.apache.commons.math3.{distribution, random, stat}
import org.economicsl.agora.markets.auctions.matching.FindFirstAcceptableOrder
import org.economicsl.agora.markets.tradables.orders.Persistent
import org.economicsl.agora.markets.tradables.orders.ask.LimitAskOrder
import org.economicsl.agora.markets.tradables.orders.bid.LimitBidOrder
import org.scalatest.{FlatSpec, Matchers}

/*
/** Class implementing a functional test of a continuous, two-sided posted price auction.
  *
  * @note this functional test also serves to demonstrate how to run a bare bones auction simulation.
  */
class KDoubleAuctionSpec extends FlatSpec with Matchers {

  val auction = {

    val tradable = TestTradable()
    val askOrderMatchingRule = FindFirstAcceptableOrder[LimitAskOrder, LimitBidOrder with Persistent]()
    val bidOrderMatchingRule = FindFirstAcceptableOrder[LimitBidOrder, LimitAskOrder with Persistent]()
    val k = 0.5
    KDoubleAuction(askOrderMatchingRule, bidOrderMatchingRule, k, tradable)

  }

  "Average price of a Fill generated by a continuous.KDoubleAuction" should "be (close to!) 100" in {

    // Define a source for randomly generated orders...
    val orderGenerator: RandomOrderGenerator = {

      val seed = 42
      val prng = new random.MersenneTwister(seed)

      // specify the sampling distribution for prices (could use different distributions for ask and bid prices...)
      val (minAskPrice, maxAskPrice) = (50, 200)
      val askPriceDistribution = new distribution.UniformRealDistribution(prng, minAskPrice, maxAskPrice)

      val (minBidPrice, maxBidPrice) = (1, 150)
      val bidPriceDistribution = new distribution.UniformRealDistribution(prng, minBidPrice, maxBidPrice)

      // specify the sampling distribution for quantities could use different distributions for ask and bid quantities...)
      val (minQuantity, maxQuantity) = (1, 1)
      val askQuantityDistribution = new distribution.UniformIntegerDistribution(prng, minQuantity, maxQuantity)
      val bidQuantityDistribution = new distribution.UniformIntegerDistribution(prng, minQuantity, maxQuantity)

      RandomOrderGenerator(prng, askPriceDistribution, askQuantityDistribution, bidPriceDistribution, bidQuantityDistribution)

    }

    // generate a sufficiently large sample of random orders...
    val numberOrders = 1000
    val askOrderProbability = 0.5
    val orders = for { i <- 1 to numberOrders } yield orderGenerator.nextLimitOrder(askOrderProbability, auction.tradable)

    // ...feed the orders into the auction mechanism...
    val fills = orders.flatMap {
      case Left(limitAskOrder) => auction.fill(limitAskOrder)
      case Right(limitBidOrder) => auction.fill(limitBidOrder)
    }

    // ...collect and summarize the results...
    val summaryStatistics = new stat.descriptive.SummaryStatistics()
    fills.foreach(fill => summaryStatistics.addValue(fill.price.value))
    val observedAverageFillPrice = summaryStatistics.getMean.round

    // ...print to screen for reference...
    println(summaryStatistics.toString)

    // ...compute the expected average fill price...
    val minAskPrice = orderGenerator.askPriceDistribution.getSupportLowerBound
    val maxBidPrice = orderGenerator.bidPriceDistribution.getSupportUpperBound
    val expectedAverageFillPrice = 0.5 * (minAskPrice + maxBidPrice)

    // ...confirm that our expectations are correct!
    observedAverageFillPrice should be(expectedAverageFillPrice)

  }

}
*/