//  CEC2009_UF7.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package org.uma.jmetal.problem.cec2009Competition;

import org.uma.jmetal.core.Problem;
import org.uma.jmetal.core.Solution;
import org.uma.jmetal.core.Variable;
import org.uma.jmetal.encoding.solutiontype.BinaryRealSolutionType;
import org.uma.jmetal.encoding.solutiontype.RealSolutionType;
import org.uma.jmetal.util.JMetalException;

/**
 * Class representing problem CEC2009_UF7
 */
public class UF7 extends Problem {

  /**
   *
   */
  private static final long serialVersionUID = -5676351902116059856L;

  /**
   * Constructor.
   * Creates a default instance of problem CEC2009_UF7 (30 decision variables)
   *
   * @param solutionType The solutiontype type must "Real" or "BinaryReal".
   */
  public UF7(String solutionType) throws ClassNotFoundException, JMetalException {
    this(solutionType, 30); // 30 variables by default
  } // CEC2009_UF7

  /**
   * Creates a new instance of problem CEC2009_UF7.
   *
   * @param numberOfVariables Number of variables.
   * @param solutionType      The solutiontype type must "Real" or "BinaryReal".
   */
  public UF7(String solutionType, Integer numberOfVariables) throws JMetalException {
    this.numberOfVariables = numberOfVariables;
    numberOfObjectives = 2;
    numberOfConstraints = 0;
    problemName = "CEC2009_UF7";

    upperLimit = new double[this.numberOfVariables];
    lowerLimit = new double[this.numberOfVariables];

    lowerLimit[0] = 0.0;
    upperLimit[0] = 1.0;
    for (int var = 1; var < this.numberOfVariables; var++) {
      lowerLimit[var] = -1.0;
      upperLimit[var] = 1.0;
    }

    if (solutionType.compareTo("BinaryReal") == 0) {
      this.solutionType = new BinaryRealSolutionType(this);
    } else if (solutionType.compareTo("Real") == 0) {
      this.solutionType = new RealSolutionType(this);
    } else {
      throw new JMetalException("Error: solutiontype type " + solutionType + " invalid");
    }
  }

  /**
   * Evaluates a solutiontype.
   *
   * @param solution The solutiontype to evaluate.
   * @throws org.uma.jmetal.util.JMetalException
   */
  public void evaluate(Solution solution) throws JMetalException {
    Variable[] decisionVariables = solution.getDecisionVariables();

    double[] x = new double[numberOfVariables];
    for (int i = 0; i < numberOfVariables; i++) {
      x[i] = decisionVariables[i].getValue();
    }

    int count1, count2;
    double sum1, sum2, yj;
    sum1 = sum2 = 0.0;
    count1 = count2 = 0;

    for (int j = 2; j <= numberOfVariables; j++) {
      yj = x[j - 1] - Math.sin(6.0 * Math.PI * x[0] + j * Math.PI / numberOfVariables);
      if (j % 2 == 0) {
        sum2 += yj * yj;
        count2++;
      } else {
        sum1 += yj * yj;
        count1++;
      }
    }
    yj = Math.pow(x[0], 0.2);

    solution.setObjective(0, yj + 2.0 * sum1 / (double) count1);
    solution.setObjective(1, 1.0 - yj + 2.0 * sum2 / (double) count2);
  }
}
