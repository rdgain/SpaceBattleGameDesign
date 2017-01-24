package test;

import tools.ElapsedCpuTimer;
import tools.StatSummary;
import tools.Utils;
import utilities.ElapsedTimer;

import java.util.Random;

import static ontology.Constants.*;

/**
 * Created by Jialin Liu on 13/10/2016.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class RMHCTest {
    private static SpaceBattleGameSearchSpace searchSpace = new SpaceBattleGameSearchSpace();
    private static int budget = 100, nTrials = 10;
    private static double epsilon = 0.3;

    private static boolean BIASED_MUT = true;

    private static String[] paramNames = new String[]{"Missile Max Speed", "Missile Cooldown", "Missile Radius", "Missile TTL", "Grid size",
            "Grid Cell 1", "Grid Cell 2", "Grid Cell 3", "Grid Cell 4", "Grid Cell 5", "Grid Cell 6", "Grid Cell 7",
            "Grid Cell 8", "Grid Cell 9", "Grid Cell 10", "Grid Cell 11", "Grid Cell 12", "Grid Cell 13", "Grid Cell 14",
            "Grid Cell 15", "Grid Cell 16", "Blackhole Radius", "Blackhole Force", "Blackhole Penalty",
            "Safe Zone", "Bomb Radius", "Missile Type", "Resource TTL", "Resource Cooldown", "Enemy ID"};

    public static void main(String[] args) {

        // record the time stats
        StatSummary ss = new StatSummary();

        for (int i=0; i<nTrials; i++) {
            ElapsedTimer t = new ElapsedTimer();
            System.out.println(t);
            ss.add(runTrial(args));
        }

        System.out.println("Best fitness stats: ");
        System.out.println(ss);

    }

    public static double runTrial(String[] args) {
        if (args != null && args.length > 1) {
            budget = Integer.parseInt(args[0]);
            if (args.length > 2) nTrials = Integer.parseInt(args[1]);
            if (args.length > 3) BIASED_MUT = Boolean.parseBoolean(args[2]);
        }

        Random rdm = new Random();

        int[] params = new int[searchSpace.nDims()];
        int[] paramIDX = new int[]{3, 2, 4, -1, 26, 21, 0, 1, 29, 23, 24, 22, 28, 27, 25};

        int[][] blackHole = new int[][] {{5}, {5, 7, 6, 8}, {12, 6, 8, 9, 7, 5, 10, 11, 13}, {6, 17, 19, 12, 18, 13, 7, 10, 5, 15, 9, 20, 11, 8, 16, 14}};



        int ai1 = -1; // STUPID
        int ai2 = -1; // MEDIUM
        int ai3 = -1; // SMART
        int resample = 1;
        int startPoint = -1;
        if(args != null && args.length==4) {
            ai1 = Integer.parseInt(args[3]);
            ai2 = Integer.parseInt(args[4]);
            resample = Integer.parseInt(args[5]);
            startPoint = Integer.parseInt(args[6]);
            params = GameDesign.initParams[startPoint];
        } else {
            ai1 = 0; //OneStep
            ai2 = 3; //RAS
            ai3 = 5; //MCTS
            for (int i=0; i<params.length; i++) {
                params[i] = searchSpace.getRandomValue(i);
            }
        }

        System.out.println("Initialising ....");
        printArray("InitParams", params);


        double bestSoFarFit = GameDesign.fitness(getFitness(ai1, ai2, ai3, params, resample));

        double newFitness;

        int buffer = 1;
        String str = "";
        int t = 0;
        int T = (int) Math.floor(budget/2/resample);

        System.out.println(resample + " " + t + " (Fitness: " + bestSoFarFit + ")");

//        params[4] = 1;
//        params[29] = 0;
//        double[] diffs = new double[1];
//        int p = 0;
//        for (int i = 5; i < 6; i++) {
//            for (int j = 5; j < 6; j++) {
//                if (i != j)
//                    params[j] = 0;
//            }
//            params[i] = 0;
//            double fitness1 = getFitness(ai1, ai2, ai3, params, resample);
//            params[i] = 1;
//            double fitness2 = getFitness(ai1, ai2, ai3, params, resample);
//            diffs[p] = fitness2 - fitness1;
//            System.out.println(i + " FITNESS " + (fitness2-fitness1));
//            p++;
//        }
//
//        for (int i = 0; i < 16; i++)
//            System.out.print(diffs[i] + " ");


        while (t < T) {

            int gridSizeIdx = 4; // index of grid size in evolvable param list (GameDesign class)


            int[] mutatedParams = new int[params.length];
            System.arraycopy(params, 0, mutatedParams, 0, params.length);

            //Get index of element to mutate using softmax
            int mutatedIdx;

            if (BIASED_MUT) {
                mutatedIdx = paramIDX[softMax(paramIDX.length)];
                if (mutatedIdx == -1) {
                    // Chosen to mutate a grid cell, so decide which one
                    mutatedIdx = blackHole[params[gridSizeIdx] - 1][softMax(blackHole[params[gridSizeIdx] - 1].length)];
                }
            } else {

                mutatedIdx = rdm.nextInt(params.length);

                int numCells = params[gridSizeIdx]*params[gridSizeIdx];
                int maxCells = MAX_GRID_SIZE*MAX_GRID_SIZE;
                int extraCells = maxCells - numCells;

                if (mutatedIdx != gridSizeIdx && params[gridSizeIdx] < MAX_GRID_SIZE) { //gridsize not chosen, reduce search space by ignoring the extra grid cells
                    int[] paramList = new int[params.length - extraCells];
                    int k = 0;
                    for (int i = 0; i < params.length; i++) {
                        if (i < gridSizeIdx + numCells + 1 || i > gridSizeIdx + maxCells) {
                            paramList[k] = i;
                            k++;
                        }
                    }
                    mutatedIdx = paramList[rdm.nextInt(paramList.length)];
                }
            }

            int mutatedValue = searchSpace.getDifferentRandomValue(mutatedIdx, params[mutatedIdx]);

            mutatedParams[mutatedIdx] = mutatedValue;
            /** evaluate offspring */
            System.out.println("-------------------------");
            printArray("MutatedParam = " + mutatedIdx + " (" + paramNames[mutatedIdx] + "); NewParams", mutatedParams);
            newFitness = GameDesign.fitness(getFitness(ai1, ai2, ai3, mutatedParams, resample));

            /** evaluate parent */
            System.out.println("-------------------------");
            printArray("OldParams", params);
            bestSoFarFit = GameDesign.fitness(getFitness(ai1, ai2, ai3, params, resample));

            if(newFitness >= bestSoFarFit) {
                params = mutatedParams;
                bestSoFarFit = newFitness;
                buffer = 1;
            } else {
                buffer++;
            }

            t++;

            System.out.println("-------------------------");
            printArray("BestParams", params);
            System.out.println(t + " (BestFitness: " + bestSoFarFit + ")");
            System.out.println("-------------------------");

        }

        return bestSoFarFit;
    }

    private static double getFitness(int ai1, int ai2, int ai3, int[] params, int resample) {
        double[] res1 = GameDesign.playNWithParams(ai1, params, resample);
        double[] res2 = GameDesign.playNWithParams(ai2, params, resample);
        double[] res3 = GameDesign.playNWithParams(ai3, params, resample);

        int lowBound = 0, highBound = 100, bonus = 1000;

        double newPoints1 = (Utils.normalise(res1[1], lowBound, highBound) + res1[0] * bonus) - (Utils.normalise(res1[3], lowBound, highBound) + res1[2] * bonus);
        double newPoints2 = (Utils.normalise(res2[1], lowBound, highBound) + res2[0] * bonus) - (Utils.normalise(res2[3], lowBound, highBound) + res2[2] * bonus);
        double newPoints3 = (Utils.normalise(res3[1], lowBound, highBound) + res3[0] * bonus) - (Utils.normalise(res3[3], lowBound, highBound) + res3[2] * bonus);

        double compare32 = newPoints3 - newPoints2;
        double compare21 = newPoints2 - newPoints1;
        return Math.min(compare32, compare21);
    }

    private static void printArray(String text, int[] params) {
        String s = text + ": ";
        for (int i=0; i<params.length; i++) {
            if (i > 0) s += ", ";
            s += params[i];
        }
        System.out.println(s);
    }


    private static int softMax(int L) {
        double[] p = new double[L];
        double sum = 0, psum = 0;
        for (int i = 0; i < L; i++) {
            sum += Math.pow(Math.E, -(i + 1));
        }
        double prob = Math.random();
        if (prob < epsilon) {
            return new Random().nextInt(L);
        } else {
            for (int i = 0; i < L; i++) {
                p[i] = Math.pow(Math.E, -(i + 1)) / sum;
                psum += p[i];
                if (psum > prob) {
                    return i;
                }
            }
            return -100;
        }
    }
}
