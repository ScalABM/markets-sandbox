/*
Copyright 2016 David R. Pugh

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
package markets.engines

import markets.mechanisms.MatchingMechanism
import markets.orders.{AskOrder, BidOrder}


/** A matched pair of ask and bid orders generated by some
  * [[MatchingMechanism `MatchingMechanism`]].
  *
  * @param askOrder
  * @param bidOrder
  * @param price
  * @param quantity
  * @param residualAskOrder
  * @param residualBidOrder
  */
case class Matching(askOrder: AskOrder,
                    bidOrder: BidOrder,
                    price: Long,
                    quantity: Long,
                    residualAskOrder: Option[AskOrder],
                    residualBidOrder: Option[BidOrder]) {

  require(0 < price, s"Price $price is not strictly positive!")
  require(price < Long.MaxValue, s"Price $price must be strictly less than ${Long.MaxValue}!")

}
