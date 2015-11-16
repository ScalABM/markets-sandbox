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
package markets.orders.orderings

import markets.orders.OrderLike


object TimeOrdering extends Ordering[OrderLike] with TimePriority {

  def compare(order1: OrderLike, order2: OrderLike): Int = {
    if (hasTimePriority(order1, order2)) {
      -1
    } else {
      1
    }

  }

}