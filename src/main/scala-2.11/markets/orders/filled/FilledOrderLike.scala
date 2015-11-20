/*
Copyright 2015 David R. Pugh, J. Doyne Farmer, and Dan F. Tang

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
package markets.orders.filled

import akka.actor.ActorRef

import markets.MessageLike
import markets.tradables.Tradable


trait FilledOrderLike extends MessageLike {

  def counterParties: (ActorRef, ActorRef)

  def tradable: Tradable

  def price: Long

  def quantity: Long

  require(price > 0, "Price must be strictly positive.")

  require(quantity > 0, "Quantity must be strictly positive.")

}
