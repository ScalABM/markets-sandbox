package org.economicsl.agora.markets.auctions.mutable.orderbooks

import java.util.UUID

import org.economicsl.agora.markets.tradables.orders.bid.BidOrder
import org.economicsl.agora.markets.tradables.Tradable
import org.economicsl.agora.markets.tradables.orders.Persistent

import scala.collection.mutable


class SortedBidOrderBook[B <: BidOrder with Persistent](val tradable: Tradable)(implicit ordering: Ordering[B])
  extends BidOrderBook[B] with SortedOrders[B] {

  /* underlying collection used to store `Order` instances. */
  protected[orderbooks] val existingOrders = mutable.HashMap.empty[UUID, B]

  protected[orderbooks] val sortedOrders = mutable.TreeSet.empty[B](ordering)

}


object SortedBidOrderBook {

  /** Create a `SortedBidOrderBook` instance for a particular `Tradable`.
    *
    * @param tradable all `BidOrder` instances contained in the `SortedBidOrderBook` should be for the same `Tradable`.
    * @tparam B type of `BidOrder` stored in the order book.
    */
  def apply[B <: BidOrder with Persistent](tradable: Tradable)(implicit ordering: Ordering[B]): SortedBidOrderBook[B] = {
    new SortedBidOrderBook[B](tradable)(ordering)
  }

}