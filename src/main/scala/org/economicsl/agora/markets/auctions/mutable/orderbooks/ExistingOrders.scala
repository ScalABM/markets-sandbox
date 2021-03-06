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
package org.economicsl.agora.markets.auctions.mutable.orderbooks

import java.util.UUID

import org.economicsl.agora.markets.tradables.Tradable
import org.economicsl.agora.markets.tradables.orders.{Order, Persistent}

import scala.collection.mutable


/** Mixin trait defining "write" methods for an underlying collection of `Order` instances.
  *
  * @tparam O the type of `Order with Persistent` stored in a `OrderBook`.
  */
trait ExistingOrders[O <: Order with Persistent] {

  /** All `Order` instances must be for the same `Tradable`. */
  def tradable: Tradable

  /** Add an new `Order` to the `OrderBook`.
    *
    * @param order the `Order` that should be added to the `OrderBook`.
    * @note Following the design of the University of Michigan Auction Bot, an `issuer` can have only one active order
    *       at a time: a new order added to the `OrderBook` supersedes any existing order with the same issuer. This
    *       requirement is not a restriction per se as the general definition of an `Order` allows an agent to
    *       potentially specify any arbitrary function.
    */
  def add(order: O): Unit = {
    require(order.tradable == tradable)
    existingOrders += (order.issuer -> order)
  }

  /** Remove all `Order` instances from the `OrderBook`. */
  def clear(): Unit = existingOrders.clear()

  /** Remove and return the head `Order` of the `OrderBook`.
    *
    * @return `None` if the `OrderBook` is empty; `Some(order)` otherwise.
    */
  def remove(): Option[O] = existingOrders.headOption.flatMap { case (uuid, order) => remove(uuid) }

  /** Remove and return an existing `Order` from the `OrderBook`.
    *
    * @param uuid the `UUID` for the order that should be removed from the `OrderBook`.
    * @return `None` if the `uuid` is not found in the order book; `Some(order)` otherwise.
    */
  def remove(uuid: UUID): Option[O] = existingOrders.remove(uuid)

  /* underlying collection used to store `Order` instances. */
  protected[orderbooks] def existingOrders: mutable.Map[UUID, O]

}

