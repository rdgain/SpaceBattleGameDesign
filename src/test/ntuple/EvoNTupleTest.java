package test.ntuple;


import bandits.MBanditEA;
import evodef.EvoAlg;
import evodef.SearchSpace;
import evodef.SolutionEvaluator;

import test.SpaceBattleGameSearchSpace;
import utilities.ElapsedTimer;
import utilities.StatSummary;


/**
 * Created by sml on 17/11/2016.
 */

public class EvoNTupleTest {

	public static SearchSpace searchSpace = new SpaceBattleGameSearchSpace();
	
    public static void main(String[] args) {

        // the number of bandits is equal to the size of the array
        int nDims = 5;
        int nTrials = 50;
        int nFitnessEvals = 100;

//        double kExplore = 1;
//        int nNeighbours = 10;
        
        EvoAlg ea = new MBanditEA();


        ea = new NTupleBanditEA();
        
        SolutionEvaluator evaluator = new TenFitnessEval(searchSpace.nDims());

        System.out.println("Best fitness stats:");
        System.out.println(runTrials(ea, evaluator, nTrials, nFitnessEvals));

        // evaluator.
    }

    public static StatSummary runTrials(EvoAlg ea, SolutionEvaluator evaluator, int nTrials, int nFitnessEvals) {

        // record the time stats
        StatSummary ss = new StatSummary();

        for (int i=0; i<nTrials; i++) {
            ElapsedTimer t = new ElapsedTimer();
            evaluator.reset();
            ea.runTrial(evaluator, nFitnessEvals);
            System.out.println(t);
            evaluator.logger().report();
            System.out.println();
            ss.add(evaluator.logger().ss.max());
        }
        return ss;
    }
}
