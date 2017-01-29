package test.ntuple;

import evodef.*;
import evogame.Mutator;
import test.GameDesign;
import utilities.Picker;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by sml on 09/01/2017.
 *
 */

public class NTupleBanditEA  implements EvoAlg {

    NTupleSystem nTupleSystem;

    // the exploration rate normally called K or C - called kExplore here for clarity
    double kExplore = 1.0;
    // the number of neighbours to explore around the current point each time
    // they are only explored IN THE FITNESS LANDSCAPE MODEL, not by sampling the fitness function
    int nNeighbours = 100;

    // when searching for the best solution overall, at the end of the run
    // we ask the NTupleMemory to explore a neighbourhood around each
    // of the points added during the search
    // this param controls the size of the neighbourhood
    int neighboursWhenFindingBest = 10;


    public NTupleBanditEA(double kExplore, int nNeighbours) {
        this.kExplore = kExplore;
        this.nNeighbours = nNeighbours;
    }

    public NTupleBanditEA() {
    }

    @Override
    public int[] runTrial(SolutionEvaluator evaluator, int nEvals) {

        // set  up some convenient references
        SearchSpace searchSpace = EvoNTupleTest.searchSpace;//evaluator.searchSpace();
        EvolutionLogger logger = evaluator.logger();
    //    System.out.println("before mutator "+searchSpace);
        Mutator mutator = new Mutator(searchSpace);

        // create an NTuple fitness landscape model
     //   System.out.println("before NTupleSystem "+searchSpace);
        nTupleSystem = new NTupleSystem(searchSpace);
        nTupleSystem.addTuples();

        // then each time around the loop try the following
        // create a neighbourhood set of points and pick the best one that combines it's exploitation and evaluation scores

        int[] params = new int[searchSpace.nDims()];
       
          for (int i=0; i<params.length; i++) {
            params[i] = searchSpace.getRandomValue(i);
          }
        
        int[] p = params;//SearchSpaceUtil.randomPoint(searchSpace);

        // nTupleSystem.printDetailedReport();

        int ai1 = 0; //OneStep
        int ai2 = 3; //RAS
        int ai3 = 5; //MCTS

        while (evaluator.nEvals() < nEvals) {

        	System.out.println("evaluation "+evaluator.nEvals());
            // each time around the loop we make one fitness evaluation of p
            // and add this NEW information to the memory


            double fitness = evaluator.getFitness(ai1, ai2, ai3, p, 1);
            nTupleSystem.addPoint(p, fitness);

            EvaluateChoices evc = new EvaluateChoices(nTupleSystem, kExplore);
            // evc.add(p);

            // and then explore the neighbourhood around p, balancing exploration and exploitation
            // depending on the mutation function, some of the neighbours could be far away
            // or some of them could be duplicates - duplicates a bit wasteful so filter these
            // out - repeat until we have the required number of unique neighbours

            while (evc.n() < nNeighbours) {
                int[] pp = mutator.randMut(p);
                evc.add(pp);
            }

            // now set the next point to explore
            p = evc.picker.getBest();
//            logger.keepBest(picker.getBest(), picker.getBestScore());
            // System.out.println("Best solution: " + Arrays.toString(picker.getBest()) + "\t: " + picker.getBestScore());

        }

        // int[] solution = nTupleSystem.getBestSolution();
        // int[] solution = nTupleSystem.getBestOfSampled();
        int[] solution = nTupleSystem.getBestOfSampledPlusNeighbours(neighboursWhenFindingBest);
        logger.keepBest(solution, evaluator.getFitness(ai1, ai2, ai3, solution, 1));
        return solution;
    }


	@Override
	public void setModel(NTupleSystem nTupleSystem) {
		// TODO Auto-generated method stub
		 this.nTupleSystem = nTupleSystem;
	}

	@Override
	public NTupleSystem getModel() {
		// TODO Auto-generated method stub
		 return nTupleSystem;
	}

}
