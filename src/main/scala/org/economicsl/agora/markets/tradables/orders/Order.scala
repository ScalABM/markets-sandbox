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
package org.economicsl.agora.markets.tradables.orders

import java.util.UUID

import org.economicsl.agora.markets.tradables.Tradable


trait Order extends Tradable {

  def issuer: UUID

  def timestamp: Long

  def tradable: Tradable

}


object Order {

  /** By default, instances of `Order` are ordered based on `timestamp` from lowest to highest */
  implicit def ordering[O <: Order]: Ordering[O] = Ordering.by(order => (order.timestamp, order.uuid))

}