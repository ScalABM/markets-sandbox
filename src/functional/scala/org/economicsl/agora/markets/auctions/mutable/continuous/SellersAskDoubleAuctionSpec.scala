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

import java.util.UUID

import org.economicsl.agora.markets.auctions.matching.FindFirstAcceptableOrder
import org.economicsl.agora.markets.tradables.TestTradable
import org.economicsl.agora.markets.tradables.orders.Persistent
import org.economicsl.agora.markets.tradables.orders.ask.LimitAskOrder
import org.economicsl.agora.markets.tradables.orders.bid.LimitBidOrder

import org.apache.commons.math3.{distribution, random, stat}
import org.scalatest.{FlatSpec, Matchers}

import scala.util.Random

/*
/** Class used to test the implementation of a `SellersAskDoubleAuction`. */
class SellersAskDoubleAuctionSpec extends FlatSpec with Matchers {

  // Create something to store simulated prices
  val summaryStatistics = new stat.descriptive.SummaryStatistics()

  // Create a single source of randomness for simulation in order to minimize indeterminacy
  val prng = new random.MersenneTwister()

  // Create a collection of traders each with it own trading rule
  val numberTraders = 5000
  val traders = for { i <- 1 to numberTraders } yield UUID.randomUUID()

  // valuation distributions are assumed common knowledge...
  val buyerValuations = new distribution.UniformRealDistribution()
  val sellerValuations = new distribution.UniformRealDistribution()

  // use the equilibrium trading rules for the test...
  val tradingRules = traders.map { trader =>

    // reservation value is private information!
    val reservationValue = prng.nextDouble()

    if (prng.nextDouble() <= 0.5) {
      Left(new SellersAskDoubleAuctionSimulation.SellerEquilibriumTradingRule(buyerValuations, trader, reservationValue, sellerValuations))
    } else {
      Right(new SellersAskDoubleAuctionSimulation.BuyerEquilibriumTradingRule(buyerValuations, trader, reservationValue, sellerValuations))
    }

  }

  val auction = {

    val tradable = TestTradable()
    val askOrderMatchingRule = FindFirstAcceptableOrder[LimitAskOrder, LimitBidOrder with Persistent]()
    val bidOrderMatchingRule = FindFirstAcceptableOrder[LimitBidOrder, LimitAskOrder with Persistent]()
    SellersAskDoubleAuction(askOrderMatchingRule, bidOrderMatchingRule, tradable)

  }

  "Average price of a Fill generated by a continuous.SellersAskDoubleAuction" should "be roughly 2/3" in {

    // simple for loop that actually runs a simulation...
    val orders = Random.shuffle(tradingRules).map {
      case Left(sellerTradingRule) => Left(sellerTradingRule(auction.tradable))
      case Right(buyerTradingRule) => Right(buyerTradingRule(auction.tradable))
    }

    // ...feed the orders into the auction mechanism...amount of parallelism in this step varies!
    val fills = orders.flatMap {
      case Left(limitAskOrder) => auction.fill(limitAskOrder)
      case Right(limitBidOrder) => auction.fill(limitBidOrder)
    }

    // ...collect the generated prices...this step is trivially parallel!
    fills.foreach(fill => summaryStatistics.addValue(fill.price.value))

    // ...print to screen for reference...
    println(summaryStatistics.toString)

    // ...compute the expected average fill price...todo: why is this the correct answer!
    val expectedAverageFillPrice = 2 / 3.0

    // ...confirm that our expectations are correct!
    assert(stat.inference.TestUtils.tTest(expectedAverageFillPrice, summaryStatistics, 0.01))

  }

}
*/