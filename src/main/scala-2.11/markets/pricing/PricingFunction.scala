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
package markets.pricing


import java.util.UUID

import markets.orderbooks.OrderBook
import markets.tradables.orders.Order
import markets.tradables.Price

import scala.collection.GenMap


/** Trait defining the interface for a `PricingFunction`. */
trait PricingFunction[O1 <: Order with Price, O2 <: Order with Price] extends ((O1, O2) => Long) {

  def apply(order1: O1, order2: O2): Long

  def apply(orderBook1: OrderBook[O1, GenMap[UUID, O1]], orderBook2: OrderBook[O2, GenMap[UUID, O2]]): Long

}
