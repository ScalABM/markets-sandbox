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
package markets.engines.orderbooks.mutable

import markets.engines.orderbooks.GenericOrderBooks
import markets.orders.{AskOrder, BidOrder}

import scala.collection.mutable


/** Mixin trait providing generic mutable order books.
  *
  * @tparam A the type of underlying collection used to store the ask orders.
  * @tparam B the type of underlying collection used to store the bid orders.
  */
trait GenericMutableOrderBooks[+A <: mutable.Iterable[AskOrder], +B <: mutable.Iterable[BidOrder]]
  extends GenericOrderBooks[A, B]
