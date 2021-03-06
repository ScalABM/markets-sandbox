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

import org.economicsl.agora.markets.tradables.Tradable


/** A mixin trait that uses a total `Ordering` to express preferences over a particular type of `Tradable`.
  *
  * @tparam T the type of `Tradable` over which the `Ordering` is defined.
  * @note any `Ordering` implies a `max` operator that can be used as an `operator` to compare two `Tradable` instances.
  */
trait Preferences[T <: Tradable] extends Operator[T] {
  this: Order =>

  /** An `Ordering` defined over a particular type of `Tradable`. */
  def ordering: Ordering[T]

  /** The `max` operator can be used to select the preferred of two `Tradable` instances. */
  def operator: (T, T) => T = ordering.max

}