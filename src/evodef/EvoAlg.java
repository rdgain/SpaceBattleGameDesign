package evodef;

import test.ntuple.NTupleSystem;

/**
 * Created by sml on 16/08/2016.
 */
public interface EvoAlg {

    int[] runTrial(SolutionEvaluator evaluator, int nEvals);
    void setModel(NTupleSystem nTupleSystem);
    NTupleSystem getModel();

}

