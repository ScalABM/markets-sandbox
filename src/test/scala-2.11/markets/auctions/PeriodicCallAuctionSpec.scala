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
package markets.auctions

import markets.RandomOrderGenerator
import markets.orders.{AskOrder, BidOrder}
import markets.tradables.Tradable
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

import scala.util.Random


class PeriodicCallAuctionSpec extends FeatureSpec
  with GivenWhenThen
  with Matchers {

  def prng = new Random()

  val initialPrice = 1

  val testTradable = Tradable("GOOG")


  feature("A PeriodicCallAuction should be able to compute aggregate demand.") {

    scenario("A PeriodicCallAuction with empty order books should have zero aggregate demand.") {

      val auctionMechanism = PeriodicCallAuction(initialPrice, testTradable)
      val randomPrice = prng.nextDouble()
      val demand = auctionMechanism.aggregateDemand(randomPrice)
      demand should be(0)

    }

    scenario("PeriodicCallAuction should have non-negative aggregate demand.") {

      Given("a PeriodicCallAuction with non-empty order books...")
      val auctionMechanism = PeriodicCallAuction(initialPrice, testTradable)

      val numberOrders = prng.nextInt(1000)
      val randomOrders = for (i <- 1 to numberOrders) yield {
        RandomOrderGenerator.randomOrder(prng, tradable = testTradable)
      }
      randomOrders.foreach {
        case order: AskOrder => auctionMechanism.askOrderBook.add(order)
        case order: BidOrder => auctionMechanism.bidOrderBook.add(order)
      }

      Then("...the aggregate demand function should always return a non-negative value.")
      val randomPrice = Double.MaxValue * prng.nextDouble()
      val demand = auctionMechanism.aggregateDemand(randomPrice)
      assert(demand >= 0)

    }

  }

  feature("A PeriodicCallAuction should be able to compute excess demand.") {

    scenario("PeriodicCallAuction with empty order books should have zero excess demand.") {

      val auctionMechanism = PeriodicCallAuction(initialPrice, testTradable)
      val randomPrice = Double.MaxValue * prng.nextDouble()
      val excessDemand = auctionMechanism.excessDemandFunction.value(randomPrice)
      excessDemand should be(0)

    }

    scenario("PeriodicCallAuction with empty ask order book should have non-negative excess demand.") {

      Given("a PeriodicCallAuction that contains some bid orders but no ask orders...")
      val auctionMechanism = PeriodicCallAuction(initialPrice, testTradable)

      val numberOrders = prng.nextInt(1000)
      val randomBidOrders = for (i <- 1 to numberOrders) yield {
        RandomOrderGenerator.randomBidOrder(prng, tradable = testTradable)
      }
      randomBidOrders.foreach(order => auctionMechanism.bidOrderBook.add(order))

      Then("...the excess demand function should always return a non-negative value.")
      val randomPrice = Double.MaxValue * prng.nextDouble()
      val excessDemand = auctionMechanism.excessDemandFunction.value(randomPrice)
      assert(excessDemand >= 0)

    }

    scenario("PeriodicCallAuction with empty bid order book should have non-positive excess demand.") {

      Given("a PeriodicCallAuction that contains some ask orders but no bid orders...")
      val auctionMechanism = PeriodicCallAuction(initialPrice, testTradable)

      val numberOrders = prng.nextInt(1000)
      val randomAskOrders = for (i <- 1 to numberOrders) yield {
        RandomOrderGenerator.randomAskOrder(prng, tradable = testTradable)
      }
      randomAskOrders.foreach(order => auctionMechanism.askOrderBook.add(order))

      Then("...the excess demand function should always return a non-positive value.")
      val randomPrice = Double.MaxValue * prng.nextDouble()
      val excessDemand = auctionMechanism.excessDemandFunction.value(randomPrice)
      println(auctionMechanism.aggregateDemand(randomPrice))
      println(auctionMechanism.aggregateSupply(randomPrice))
      assert(excessDemand <= 0)

    }

  }

  feature("A PeriodicCallAuction should be able to compute aggregate supply.") {

    scenario("A PeriodicCallAuction with empty order books should have zero aggregate supply.") {

      val auctionMechanism = PeriodicCallAuction(initialPrice, testTradable)
      val supply = auctionMechanism.aggregateSupply(100)
      supply should be(0)

    }

    scenario("PeriodicCallAuction should have non-negative aggregate supply.") {

      Given("a PeriodicCallAuction with non-empty order books...")
      val auctionMechanism = PeriodicCallAuction(initialPrice, testTradable)

      val numberOrders = prng.nextInt(1000)
      val randomOrders = for (i <- 1 to numberOrders) yield {
        RandomOrderGenerator.randomOrder(prng, tradable = testTradable)
      }
      randomOrders.foreach {
        case order: AskOrder => auctionMechanism.askOrderBook.add(order)
        case order: BidOrder => auctionMechanism.bidOrderBook.add(order)
      }

      Then("...the aggregate supply function should always return a non-negative value.")
      val randomPrice = Double.MaxValue * prng.nextDouble()
      val supply = auctionMechanism.aggregateSupply(randomPrice)
      assert(supply >= 0)

    }

  }

  feature("A PeriodicCallAuction with empty order books should not generate any matchings.") {

    scenario("Both ask and bid order books are empty") {

      val auctionMechanism = PeriodicCallAuction(initialPrice, testTradable)
      val filledOrders = auctionMechanism.fill()
      filledOrders should be(None)
    }

    scenario("PeriodicCallAuction with an empty bid order book should not generate matchings.") {

      val auctionMechanism = PeriodicCallAuction(initialPrice, testTradable)
      val order = RandomOrderGenerator.randomAskOrder(prng, 0.5, tradable = testTradable)
      auctionMechanism.askOrderBook.add(order)
      val filledOrders = auctionMechanism.fill()
      filledOrders should be(None)

    }

    scenario("PeriodicCallAuction with an empty ask order book should not generate matchings.") {

      val auctionMechanism = PeriodicCallAuction(initialPrice, testTradable)
      val order = RandomOrderGenerator.randomBidOrder(prng, 0.5, tradable = testTradable)
      auctionMechanism.bidOrderBook.add(order)
      val filledOrders = auctionMechanism.fill()
      filledOrders should be(None)

    }

  }

  feature("A PeriodicCallAuction should be able to find a market clearing price.") {

    scenario("PeriodicCallAuction should have a strictly positive market clearing price.") {

      Given("a PeriodicCallAuction with non-empty order books...")
      val auctionMechanism = PeriodicCallAuction(initialPrice, testTradable)

      val numberOrders = prng.nextInt(1000)
      val randomOrders = for (i <- 1 to numberOrders) yield {
        RandomOrderGenerator.randomOrder(prng, tradable = testTradable)
      }
      randomOrders.foreach {
        case order: AskOrder => auctionMechanism.askOrderBook.add(order)
        case order: BidOrder => auctionMechanism.bidOrderBook.add(order)
      }

      Then("...the findMarketClearingPrice should always return a positive value.")
      val maxEval = 1000
      val price = auctionMechanism.findMarketClearingPrice(maxEval)
      assert(price > 0)

    }

  }
}