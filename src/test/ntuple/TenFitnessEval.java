package test.ntuple;

import evodef.EvolutionLogger;
import evodef.SearchSpace;
import evodef.SolutionEvaluator;
import test.GameDesign;
import tools.Utils;

import java.util.Random;

/**
 * Created by sml on 17/11/2016.
 */
public class TenFitnessEval extends TenSpace implements SolutionEvaluator{
    static Random random = new Random();
    static double noise = 0.1;
    public TenFitnessEval(int nDims) {
        super(nDims);
        if (nDims < 5) throw new RuntimeException("Number of dimensions less than 5: " + nDims);
        reset();
    }
    EvolutionLogger logger;

    @Override
    public void reset() {
        logger = new EvolutionLogger();
    }

    @Override
    public double evaluate(int[] p) {
//        for (int i=0; i<bits.length; i++) {
//            // if (bits)
//            // should do a sanity check, but can't be bothered...
//        }
        // System.out.println("Evaluating");
        double fitness = p[0] * p[1] - 2 * p[2] * p[3] + p[3] * p[4] + 10 * p[2] + random.nextGaussian() * noise;
        logger.log(fitness, p, false);
        return fitness;
    }


    @Override
    public Double optimalIfKnown() {
        return null;
    }

    @Override
    public boolean optimalFound() {
        return false;
    }

    @Override
    public SearchSpace searchSpace() {
        return this;
    }

    @Override
    public int nEvals() {
        return logger.nEvals();
    }

    @Override
    public EvolutionLogger logger() {
        return logger;
    }

	@Override
	public double getFitness(int ai1, int ai2, int ai3, int[] params, int resample) {
	    double[] res1 = GameDesign.playNWithParams(ai1, params, resample);
	    double[] res2 = GameDesign.playNWithParams(ai2, params, resample);
	    double[] res3 = GameDesign.playNWithParams(ai3, params, resample);

	    int lowBound = 0, highBound = 100, bonus = 1000;

	    double newPoints1 = (Utils.normalise(res1[1], lowBound, highBound) + res1[0] * bonus) - (Utils.normalise(res1[3], lowBound, highBound) + res1[2] * bonus);
	    double newPoints2 = (Utils.normalise(res2[1], lowBound, highBound) + res2[0] * bonus) - (Utils.normalise(res2[3], lowBound, highBound) + res2[2] * bonus);
	    double newPoints3 = (Utils.normalise(res3[1], lowBound, highBound) + res3[0] * bonus) - (Utils.normalise(res3[3], lowBound, highBound) + res3[2] * bonus);

	    double compare32 = newPoints3 - newPoints2;
	    double compare21 = newPoints2 - newPoints1;
	    logger.log(Math.min(compare32, compare21), params, false);
	    return Math.min(compare32, compare21);
	  }
}
