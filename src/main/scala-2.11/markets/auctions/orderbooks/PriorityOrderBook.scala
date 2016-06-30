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
package markets.auctions.orderbooks

import java.util.UUID

import markets.orders.Order
import markets.orders.limit.LimitOrder
import markets.tradables.Tradable

import scala.collection.mutable


/** Class for modeling an `PriorityOrderBook` where the underlying collection of orders is sorted.
  *
  * @param tradable `Orders` contained in the `PriorityOrderBook` should be for the same `Tradable`.
  * @param ordering an `Ordering` used to compare `Order` instances.
  * @tparam A type of `Order` stored in the order book.
  */
class PriorityOrderBook[A <: Order](tradable: Tradable)(implicit ordering: Ordering[A])
  extends OrderBook[A](tradable) {

  /** Indicates whether or not the `PriorityOrderBook` is empty. */
  override def nonEmpty: Boolean = super.nonEmpty & prioritisedOrders.nonEmpty

  /** Add an `Order` to the `PriorityOrderBook`.
    *
    * @param order the `Order` that should be added to the `PriorityOrderBook`.
    * @note Underlying implementation uses an `mutable.PriorityQueue` in order to guarantee that
    *       adding an `Order` to the `PriorityOrderBook` is an `O(1)` operation.
    */
  override def add(order: A): Unit = {
    super.add(order)
    prioritisedOrders.enqueue(order)
  }

  /** Return the highest priority `Order` in the `PriorityOrderBook`.
    *
    * @return `None` if the order book is empty; `Some(order)` otherwise.
    * @note Underlying implementation uses a `mutable.PriorityQueue` in order to guarantee that
    *       returning the highest priority `Order` is an `O(1)` operation.
    */
  def peek: Option[A] = prioritisedOrders.headOption

  /** Remove and return the highest priority order in the order book.
    *
    * @return `None` if the order book is empty; `Some(order)` otherwise.
    * @note Underlying implementation uses a `mutable.PriorityQueue` in order to guarantee that
    *       removing the highest priority `Order` from the `PriorityOrderBook` is an `O(log n)` operation.
    */
  def poll(): Option[A] = {
    if (nonEmpty) {
      val priorityOrder = prioritisedOrders.dequeue()
      super.remove(priorityOrder.uuid) match { // remove priorityOrder from existingOrders!
        case Some(residualOrder) => assert(residualOrder == priorityOrder); Some(priorityOrder)
      }
    } else {
      None
    }
  }

  /** Return the highest priority `LimitOrder` in the `PriorityOrderBook`.
    *
    * @return `None` if the order book does not contain a `LimitOrder`; `Some(order)` otherwise.
    */
  def priorityLimitOrder: Option[A] = {
    prioritisedOrders.find(order => order.isInstanceOf[LimitOrder])
  }

  /** Remove and return an existing `Order` from the `PriorityOrderBook`.
    *
    * @param uuid the `UUID` for the order that should be removed from the `PriorityOrderBook`.
    * @return `None` if the `uuid` is not found in the order book; `Some(order)` otherwise.
    * @note Underlying implementation filters the existing `mutable.PriorityQueue`; therefore
    *       removing an `Order` is an `O(n)` operation.
    */
  override def remove(uuid: UUID): Option[A] = super.remove(uuid) match {
    case residualOrder @ Some(_) =>
      prioritisedOrders = prioritisedOrders.filterNot(order => order.uuid == uuid)
      residualOrder
    case None => None
  }

  /* Protected at package-level for testing. */
  protected[orderbooks] var prioritisedOrders = mutable.PriorityQueue.empty[A](ordering)

}


object PriorityOrderBook {

  def apply[A <: Order](tradable: Tradable)(implicit ordering: Ordering[A]): PriorityOrderBook[A] = {
    new PriorityOrderBook(tradable)(ordering)
  }

}