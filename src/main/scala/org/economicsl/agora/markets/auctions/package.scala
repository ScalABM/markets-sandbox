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
package org.economicsl.agora.markets

import org.apache.commons.math3.analysis.solvers.AllowedSolution


/** Classes for modeling auction mechanisms.
  *
  * Key high-level abstraction: Auction mechanisms combine a matching rule with a pricing rule.  Auction mechanisms can
  * be either continuous or periodic.
  */
package object auctions {

  /** Class implemented a simple configuration object for a non-linear equation solver. */
  case class BrentSolverConfig(maxEvaluations: Int,
                               min: Double,
                               max: Double,
                               startValue: Double,
                               allowedSolution: AllowedSolution,
                               relativeAccuracy: Double,
                               absoluteAccuracy: Double,
                               functionValueAccuracy: Double,
                               maximalOrder: Int)

}
