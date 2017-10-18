package org.uma.jmetal.algorithm.multiobjective.rnsgaii;

import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAII;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.selection.RankingAndPreferenceSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
@SuppressWarnings("serial")
public class RNSGAII<S extends Solution<?>> extends NSGAII<S> {


  private List<Double> interestPoint;
  private  double epsilon;

  /**
   * Constructor
   */
  public RNSGAII(Problem<S> problem, int maxEvaluations, int populationSize,
                   CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator,
                   SelectionOperator<List<S>, S> selectionOperator, SolutionListEvaluator<S> evaluator,List<Double> interestPoint,double epsilon) {
    super(problem,maxEvaluations,populationSize,crossoverOperator,mutationOperator,selectionOperator ,evaluator);
    this.interestPoint= interestPoint;
    this.epsilon =epsilon;
  }
  public void updateReferencePoint(List<Double> newReferencePoints){
    this.interestPoint = newReferencePoints;
  }
  @Override protected void initProgress() {
    evaluations = getMaxPopulationSize();
  }

  @Override protected void updateProgress() {
    evaluations += getMaxPopulationSize() ;
  }

  @Override protected boolean isStoppingConditionReached() {
    return evaluations >= maxEvaluations;
  }


  @Override protected List<S> replacement(List<S> population, List<S> offspringPopulation) {
    List<S> jointPopulation = new ArrayList<>();
    jointPopulation.addAll(population);
    jointPopulation.addAll(offspringPopulation);

    //RankingAndPreferenceSelection_Ant<S> rankingAndCrowdingSelection ;
    //rankingAndCrowdingSelection = new RankingAndPreferenceSelection_Ant<S>(getMaxPopulationSize(),interestPoint,epsilon) ;
    RankingAndPreferenceSelection<S> rankingAndCrowdingSelection ;
    rankingAndCrowdingSelection = new RankingAndPreferenceSelection<S>(getMaxPopulationSize(),interestPoint,epsilon) ;

    return rankingAndCrowdingSelection.execute(jointPopulation) ;
  }

  @Override public List<S> getResult() {
    return getNonDominatedSolutions(getPopulation());
  }


  @Override public String getName() {
    return "RNSGAII_Ant" ;
  }

  @Override public String getDescription() {
    return "Nondominated Sorting Genetic Algorithm version II" ;
  }
}
