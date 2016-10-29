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
package org.economicsl.agora.twosided.matching

import java.util.UUID

import org.economicsl.agora.{onesided, orderbooks}
import org.economicsl.agora.tradables.orders.ask.AskOrder
import org.economicsl.agora.tradables.orders.bid.BidOrder
import org.economicsl.agora.tradables.orders.Operator


class ReduceMatchingFunction[A <: AskOrder with Operator[B], B <: BidOrder with Operator[A]]
  extends MatchingFunction[A, orderbooks.OrderBook[A, collection.GenMap[UUID, A]], B, orderbooks.OrderBook[B, collection.GenMap[UUID, B]]]{

  /** One-side matching function used to match an `AskOrder` with an order book containing `BidOrder` instances. */
  val askOrderMatchingFunction = new onesided.matching.ReduceMatchingFunction[A, B]()

  /** One-side matching function used to match a `BidOrder` with an order book containing `AskOrder` instances. */
  val bidOrderMatchingFunction = new onesided.matching.ReduceMatchingFunction[B, A]()

}


