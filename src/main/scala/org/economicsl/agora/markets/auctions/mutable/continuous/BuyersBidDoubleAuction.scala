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

import org.economicsl.agora.markets.auctions.mutable.orderbooks.{AskOrderBook, BidOrderBook}
import org.economicsl.agora.markets.tradables.{Quantity, Tradable}
import org.economicsl.agora.markets.tradables.orders.Persistent
import org.economicsl.agora.markets.tradables.orders.ask.LimitAskOrder
import org.economicsl.agora.markets.tradables.orders.bid.LimitBidOrder


/** Class implementing a "Buyer's Bid" double auction as described in Satterthwaite and Williams (JET, 1989).
  *
  * @param tradable all `Order` instances must be for the same `Tradable`.
  * @note the "Buyer's Bid" double auction is a variant of the k-Double Auction mechanism described in Satterthwaite
  *       and Williams (JET, 1989). In a "Buyer's bid" double auction, the price for each `Fill` is determined by the
  *       `LimitBidOrder`; all of the profit from each trade accrues to the issuer of the `LimitAskOrder`.  Because the
  *       issuer of a `LimitAskOrder` can not influence the `Fill` price, its dominant strategy is always to truthfully
  *       reveal its private reservation value when issuing a `LimitAskOrder`. The issuer of the 'LimitBidOrder',
  *       however, clearly has an incentive to bid strictly less than its private reservation value.
  */
class BuyersBidDoubleAuction(askOrderBook: AskOrderBook[LimitAskOrder with Persistent with Quantity],
                             askOrderMatchingRule: (LimitAskOrder with Quantity, BidOrderBook[LimitBidOrder with Persistent with Quantity]) => Option[LimitBidOrder with Persistent with Quantity],
                             bidOrderBook: BidOrderBook[LimitBidOrder with Persistent with Quantity],
                             bidOrderMatchingRule: (LimitBidOrder with Quantity, AskOrderBook[LimitAskOrder with Persistent with Quantity]) => Option[LimitAskOrder with Persistent with Quantity],
                             tradable: Tradable)
  extends KDoubleAuction(askOrderBook, askOrderMatchingRule, bidOrderBook, bidOrderMatchingRule, 1, tradable)


/** Companion object for a `BuyersBidDoubleAuction`.
  *
  * Contains auxiliary constructors for a `BuyersBidDoubleAuction` as well as classes implementing the equilibrium
  * trading rules for the auction derived in Satterthwaite and Williams (JET, 1989).
  */
object BuyersBidDoubleAuction {

  /** Create an instance of a `BuyersBidDoubleAuction`.
    *
    * @param askOrderBook
    * @param askOrderMatchingRule
    * @param bidOrderBook
    * @param bidOrderMatchingRule
    * @param tradable
    * @return an instance of a `BuyersBidDoubleAuction` for a particular `Tradable`
    */
  def apply(askOrderBook: AskOrderBook[LimitAskOrder with Persistent with Quantity],
            askOrderMatchingRule: (LimitAskOrder with Quantity, BidOrderBook[LimitBidOrder with Persistent with Quantity]) => Option[LimitBidOrder with Persistent with Quantity],
            bidOrderBook: BidOrderBook[LimitBidOrder with Persistent with Quantity],
            bidOrderMatchingRule: (LimitBidOrder with Quantity, AskOrderBook[LimitAskOrder with Persistent with Quantity]) => Option[LimitAskOrder with Persistent with Quantity],
            tradable: Tradable): BuyersBidDoubleAuction = {
    new BuyersBidDoubleAuction(askOrderBook, askOrderMatchingRule, bidOrderBook, bidOrderMatchingRule, tradable)
  }

  /** Create an instance of a `BuyersBidDoubleAuction`.
    *
    * @param askOrderMatchingRule
    * @param bidOrderMatchingRule
    * @param tradable
    * @return an instance of a `BuyersBidDoubleAuction` for a particular `Tradable`
    */
  def apply(askOrderMatchingRule: (LimitAskOrder with Quantity, BidOrderBook[LimitBidOrder with Persistent with Quantity]) => Option[LimitBidOrder with Persistent with Quantity],
            bidOrderMatchingRule: (LimitBidOrder with Quantity, AskOrderBook[LimitAskOrder with Persistent with Quantity]) => Option[LimitAskOrder with Persistent with Quantity],
            tradable: Tradable): BuyersBidDoubleAuction = {
    val askOrderBook = AskOrderBook[LimitAskOrder with Persistent with Quantity](tradable)
    val bidOrderBook = BidOrderBook[LimitBidOrder with Persistent with Quantity](tradable)
    new BuyersBidDoubleAuction(askOrderBook, askOrderMatchingRule, bidOrderBook, bidOrderMatchingRule, tradable)
  }

}
