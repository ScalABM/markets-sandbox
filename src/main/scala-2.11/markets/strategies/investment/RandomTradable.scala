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
package markets.strategies.investment

import java.{lang, util}

import markets.orders.Order
import markets.tradables.Tradable
import org.apache.commons.math3.distribution.EnumeratedDistribution
import org.apache.commons.math3.random.RandomGenerator
import org.apache.commons.math3.util.Pair


trait RandomTradable[T <: Order] {
  this: InvestmentStrategy[T] =>

  def prng: RandomGenerator

  def tradablesDistribution: EnumeratedDistribution[Tradable]

  def tradablesDistributionFactory(prng: RandomGenerator,
                                   pmf: util.List[Pair[Tradable, lang.Double]]) = {
    new EnumeratedDistribution[Tradable](prng, pmf)
  }
}

