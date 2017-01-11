package test;

import tools.ElapsedCpuTimer;
import tools.Utils;

import java.util.Random;

/**
 * Created by Jialin Liu on 13/10/2016.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class RMHCTest {
  static SpaceBattleGameSearchSpace searchSpace = new SpaceBattleGameSearchSpace();
  static int budget = 100;

  static String[] paramNames = new String[]{"Missile Max Speed", "Missile Cooldown", "Missile Radius", "Missile TTL", "Grid size",
          "Grid Cell 1", "Grid Cell 2", "Grid Cell 3", "Grid Cell 4", "Grid Cell 5", "Grid Cell 6", "Grid Cell 7",
          "Grid Cell 8", "Grid Cell 9", "Grid Cell 10", "Grid Cell 11", "Grid Cell 12", "Grid Cell 13", "Grid Cell 14",
          "Grid Cell 15", "Grid Cell 16", "Blackhole Radius", "Blackhole Force", "Blackhole Penalty",
          "Safe Zone", "Bomb Radius", "Missile Type", "Resource TTL", "Resource Cooldown", "Enemy ID"};

  public static void main(String[] args) {

    Random rdm = new Random();

    int[] params = new int[searchSpace.nDims()];
    int ai1 = -1; // STUPID
    int ai2 = -1; // MEDIUM
    int ai3 = -1; // SMART
    int resample = 1;
    int startPoint = -1;
    if(args.length==4) {
      ai1 = Integer.parseInt(args[0]);
      ai2 = Integer.parseInt(args[1]);
      resample = Integer.parseInt(args[2]);
      startPoint = Integer.parseInt(args[3]);
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

    while (t < T) {

      int[] mutatedParams = new int[params.length];
      System.arraycopy(params, 0, mutatedParams, 0, params.length);
      int mutatedIdx = rdm.nextInt(params.length);
      int mutatedValue = searchSpace.getRandomValue(mutatedIdx);
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
      System.out.println(resample + " " + t + " (BestFitness: " + bestSoFarFit + ")");
      System.out.println("-------------------------");

    }
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

}
