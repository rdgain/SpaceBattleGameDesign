package test;

import ontology.Constants;
import static ontology.Constants.*;

/**
 * Created by Jialin Liu on 12/10/2016.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p>
 * Respect to Google Java Style Guide:
 * https://google.github.io/stylBeguide/javaguide.html
 */
public class GameDesign {
  public static int nbIter = 1000;
  public static int nbRuns = 10;
  public static int p1 = 5;  //
  public static int p2 = 6;  // random
  public static double target = 1.0;
  public static double maxDist = Math.max(target, 1-target);
  public static double range = Math.max( target*target, (1-target)*(1-target) );
  public static int[] indices = {2, 3, 4, 7, 8, 11, 1, 5, 6, 9, 10};

  static int[][] bounds = {
      {4, 10, 2},  // SHIP_MAX_SPEED
      {1, 5, 1},  // THRUST_SPEED
      {5, 95, 10},  // MISSILE_COST
      {1, 10, 1},  // MISSILE_MAX_SPEED
      {0, 5, 1},  // MISSILE_COOLDOWN

      {10, 100, 10},  // KILL_AWARD

      {10, 50},  // SHIP_RADIUS
      {1, 5},  // MISSILE_RADIUS
      {10, 50},  // MISSILE_MAX_TTL
      {50, 100},  // FRICTION
      {1, 60}  // RADIAN_UNIT
  };


  static int[][] initParams = {
      { 10, 3, 100, 7, 6, 50},
      { 10, 5, 0, 9, 6, 50},
      { 8, 3, 75, 3, 5, 50},
      { 8, 5, 50, 6, 3, 40},
      { 4, 4, 50, 8, 9, 50},
      { 10, 3, 100, 6, 1, 10},
      { 10, 3, 75, 3, 5, 40},
      { 4, 4, 5, 1, 5, 10},
      { 4, 2, 1, 2, 1, 40},
      { 6, 3, 50, 5, 5, 30},
      { 4, 3, 75, 9, 3, 20},
      { 8, 4, 10, 3, 9, 10},
      { 4, 1, 1, 7, 6, 10},
      { 10, 4, 50, 1, 8, 50},
      { 10, 5, 75, 6, 2, 20},
      { 4, 1, 100, 4, 3, 20},
      { 6, 4, 0, 9, 6, 50},
      { 6, 3, 0, 2, 6, 20},
      { 10, 1, 100, 6, 7, 50},
      { 6, 3, 10, 8, 8, 10},
      { 4, 2, 0, 6, 4, 10},
      { 4, 5, 50, 5, 9, 10},
      { 8, 4, 20, 2, 6, 20},
      { 4, 2, 100, 1, 3, 10},
      { 6, 1, 100, 2, 1, 20},
      { 6, 1, 100, 4, 3, 10},
      { 6, 1, 20, 8, 6, 10},
      { 4, 4, 100, 6, 1, 50},
      { 6, 2, 50, 1, 1, 40},
      { 8, 3, 50, 8, 8, 20},
      { 8, 3, 10, 1, 8, 20},
      { 8, 4, 0, 2, 5, 30},
      { 10, 4, 50, 1, 1, 10},
      { 10, 5, 50, 2, 7, 10},
      { 4, 4, 5, 7, 7, 30},
      { 8, 2, 50, 10, 8, 10},
      { 6, 2, 50, 6, 8, 20},
      { 4, 1, 75, 3, 4, 30},
      { 4, 4, 10, 2, 8, 10},
      { 6, 2, 20, 1, 4, 10},
      { 4, 4, 5, 7, 9, 30},
      { 8, 4, 10, 7, 1, 50},
      { 4, 2, 75, 5, 7, 20},
      { 6, 1, 50, 5, 5, 40},
      { 4, 2, 75, 7, 2, 10},
      { 4, 1, 10, 7, 7, 30},
      { 4, 4, 1, 2, 1, 10},
      { 4, 1, 5, 4, 2, 20},
      { 10, 4, 20, 2, 2, 10},
      { 10, 4, 20, 4, 2, 40},
      { 10, 1, 5, 4, 1, 40},
      { 6, 5, 10, 7, 2, 20},
      { 4, 4, 75, 4, 7, 20},
      { 8, 5, 20, 4, 3, 30},
      { 6, 2, 20, 8, 4, 30},
      { 4, 1, 5, 4, 6, 50},
      { 10, 3, 1, 8, 7, 40},
      { 8, 1, 50, 5, 2, 10},
      { 10, 1, 1, 7, 9, 30},
      { 8, 1, 100, 6, 7, 10},
      { 10, 4, 0, 6, 3, 30},
      { 6, 3, 1, 3, 1, 50},
      { 8, 5, 1, 10, 8, 30},
      { 6, 2, 75, 3, 1, 40},
      { 8, 4, 50, 5, 4, 50},
      { 6, 5, 75, 9, 5, 40},
      { 10, 3, 0, 9, 6, 20},
      { 10, 2, 50, 7, 4, 10},
      { 4, 3, 1, 8, 4, 50},
      { 8, 3, 1, 10, 3, 50},
      { 4, 2, 0, 7, 2, 10},
      { 8, 2, 50, 4, 6, 10},
      { 10, 5, 50, 9, 4, 40},
      { 8, 3, 5, 3, 5, 20},
      { 10, 5, 0, 6, 1, 50},
      { 10, 1, 100, 1, 7, 40},
      { 8, 5, 100, 7, 2, 20},
      { 4, 1, 0, 7, 9, 20},
      { 6, 5, 100, 7, 9, 40},
      { 6, 4, 1, 3, 7, 30},
      { 6, 4, 75, 6, 5, 30},
      { 8, 1, 50, 10, 4, 50},
      { 6, 5, 10, 5, 2, 10},
      { 6, 4, 75, 7, 1, 50},
      { 10, 4, 0, 4, 7, 40},
      { 4, 2, 50, 5, 6, 20},
      { 4, 5, 75, 10, 1, 10},
      { 4, 3, 1, 9, 1, 10},
      { 8, 4, 5, 2, 4, 20},
      { 8, 5, 50, 2, 4, 10},
      { 8, 3, 100, 9, 5, 50},
      { 4, 2, 100, 10, 7, 40},
      { 6, 5, 0, 8, 6, 50},
      { 6, 4, 75, 4, 5, 50},
      { 8, 2, 20, 4, 7, 30},
      { 6, 4, 100, 4, 8, 40},
      { 10, 1, 5, 7, 3, 20},
      { 8, 4, 20, 7, 1, 20},
      { 6, 2, 50, 9, 3, 40},
      { 4, 5, 1, 6, 4, 50}
  };

  public static double[] playNWithParams(int ai1, int[] params, int resample) {
//    ElapsedCpuTimer t = new ElapsedCpuTimer();

    setParams(params);


    double[] res = GameTest.playNAndMean(resample, ai1, ENEMY_ID);
//    System.out.println(t);

    return res;
  }


  public static void playOneWithParams(int ai1, int[] params, int resample) {
//    ElapsedCpuTimer t = new ElapsedCpuTimer();

    setParams(params);
    GameTest.playOne(ai1,ENEMY_ID,true,1);
  }


  public static double fitness(double _var) {
//    double sqDist = (var-target)*(var-target);
//    return (1 - sqDist/range);
//    double dist = Math.abs(var - target);
//    return (1 - dist/maxDist);
    return _var;
  }

  public static void setParams(int[] params) {
    MISSILE_MAX_SPEED = params[0];
    MISSILE_COOLDOWN = params[1];
    MISSILE_RADIUS = params[2];
    MISSILE_MAX_TTL = params[3];
    GRID_SIZE = params[4];

    grid1 = params[5];
    grid2 = params[6];
    grid3 = params[7];
    grid4 = params[8];
    grid5 = params[9];
    grid6 = params[10];
    grid7 = params[11];
    grid8 = params[12];
    grid9 = params[13];
    grid10 = params[14];
    grid11 = params[15];
    grid12 = params[16];
    grid13 = params[17];
    grid14 = params[18];
    grid15 = params[19];
    grid16 = params[20];

    HOLE_GRID_MAYBE = (grid1 == 0? "F" : "T") + (grid2 == 0? "F" : "T") + (grid3 == 0? "F" : "T") + (grid4 == 0? "F" : "T")
            + (grid5 == 0? "F" : "T") + (grid6 == 0? "F" : "T") + (grid7 == 0? "F" : "T") + (grid8 == 0? "F" : "T") + (grid9 == 0? "F" : "T") +
            (grid10 == 0? "F" : "T") + (grid11 == 0? "F" : "T") + (grid12 == 0? "F" : "T") + (grid13 == 0? "F" : "T") + (grid14 == 0? "F" : "T") + (grid15 == 0? "F" : "T") + (grid16 == 0? "F" : "T");

    BLACKHOLE_RADIUS = params[21];
    BLACKHOLE_FORCE = params[22];
    BLACKHOLE_PENALTY = params[23];
    SAFE_ZONE = params[24];

    BOMB_RADIUS = params[25];
    MISSILE_TYPE = params[26];

    RESOURCE_TTL = params[27];
    RESOURCE_COOLDOWN = params[28];

    ENEMY_ID = params[29];
  }

}