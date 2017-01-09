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
  static int budget = 5;

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
      ai1 = 3;
      ai2 = 0;
      ai3 = 5;
      for (int i=0; i<params.length; i++) {
        params[i] = searchSpace.getRandomValue(i);
      }
    }

    System.out.println("Initialising ....");

    String s = "";
    for (int i=0; i<params.length; i++) {
      s += " " + params[i];
    }
    System.out.println(s);

    double[] res1 = GameDesign.playNWithParams(ai1, params, resample);
    double[] res2 = GameDesign.playNWithParams(ai2, params, resample);
    double[] res3 = GameDesign.playNWithParams(ai3, params, resample);

    int lowBound = 0, highBound = 100, bonus = 1000;

    double newPoints1 = (Utils.normalise(res1[1], lowBound, highBound) + res1[0] * bonus) - (Utils.normalise(res1[3], lowBound, highBound) + res1[2] * bonus);
    double newPoints2 = (Utils.normalise(res2[1], lowBound, highBound) + res2[0] * bonus) - (Utils.normalise(res2[3], lowBound, highBound) + res2[2] * bonus);
    double newPoints3 = (Utils.normalise(res3[1], lowBound, highBound) + res3[0] * bonus) - (Utils.normalise(res3[3], lowBound, highBound) + res3[2] * bonus);

    double compare32 = newPoints3 - newPoints2;
    double compare21 = newPoints2 - newPoints1;

    double bestSoFarFit = GameDesign.fitness(compare32 - Math.abs(compare32 - compare21));

    double newFitness;

    int buffer = 1;
    String str = "";
    int t = 0;
    int T = (int) Math.floor(budget/2/resample);

    str = resample + " " + t + " " + bestSoFarFit;
    for (int i=0; i<params.length; i++) {
      str += " " + params[i];
    }
    System.out.println(str);

    while (t < T) {

      int[] mutatedParams = params;
      int mutatedIdx = rdm.nextInt(params.length);
      int mutatedValue = searchSpace.getRandomValue(mutatedIdx);
      mutatedParams[mutatedIdx] = mutatedValue;
      /** evaluate offspring */
      res1 = GameDesign.playNWithParams(ai1, mutatedParams, resample);
      res2 = GameDesign.playNWithParams(ai2, mutatedParams, resample);
      res3 = GameDesign.playNWithParams(ai3, mutatedParams, resample);

//      newWinRate1 = res1[0];
      newPoints1 = (Utils.normalise(res1[1], lowBound, highBound) + res1[0] * bonus) - (Utils.normalise(res1[3], lowBound, highBound) + res1[2] * bonus);

//      newWinRate2 = res2[0];
      newPoints2 = (Utils.normalise(res2[1], lowBound, highBound) + res2[0] * bonus) - (Utils.normalise(res2[3], lowBound, highBound) + res2[2] * bonus);

//      newWinRate3 = res3[0];
      newPoints3 = (Utils.normalise(res3[1], lowBound, highBound) + res3[0] * bonus) - (Utils.normalise(res3[3], lowBound, highBound) + res3[2] * bonus);

      compare32 = newPoints3 - newPoints2;
      compare21 = newPoints2 - newPoints1;
//      double compare31 = newPoints3 - newPoints1;

      newFitness = GameDesign.fitness(compare32 - Math.abs(compare32 - compare21));

      /** evaluate parent */
      res1 = GameDesign.playNWithParams(ai1, params, resample);
      res2 = GameDesign.playNWithParams(ai2, params, resample);
      res3 = GameDesign.playNWithParams(ai3, params, resample);
//      bestSoFarPoints = (res1[1] + bestSoFarPoints*buffer) / (buffer+1);
//      bestSoFar = (res1[0] + bestSoFar*buffer) / (buffer+1);

      newPoints1 = (Utils.normalise(res1[1], lowBound, highBound) + res1[0] * bonus) - (Utils.normalise(res1[3], lowBound, highBound) + res1[2] * bonus);
      newPoints2 = (Utils.normalise(res2[1], lowBound, highBound) + res2[0] * bonus) - (Utils.normalise(res2[3], lowBound, highBound) + res2[2] * bonus);
      newPoints3 = (Utils.normalise(res3[1], lowBound, highBound) + res3[0] * bonus) - (Utils.normalise(res3[3], lowBound, highBound) + res3[2] * bonus);

      compare32 = newPoints3 - newPoints2;
      compare21 = newPoints2 - newPoints1;

      bestSoFarFit = GameDesign.fitness(compare32 - Math.abs(compare32 - compare21));

      if(newFitness >= bestSoFarFit) {
        params = mutatedParams;
//        bestSoFar = newWinRate;
        bestSoFarFit = newFitness;
//        bestSoFarPoints = newPoints;
        buffer = 1;
      } else {
        buffer++;
      }

      t++;

      str = resample + " " + t + " " + bestSoFarFit;
      for (int i=0; i<params.length; i++) {
        str += " " + params[i];
      }
      System.out.println(str);

    }
  }
}
