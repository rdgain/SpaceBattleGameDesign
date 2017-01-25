package test;

import evodef.SearchSpace;

import java.util.Random;

/**
 * Created by Jialin Liu on 14/10/2016.
 * CSEE, University of Essex, UK
 * Email: jialin.liu@essex.ac.uk
 * <p>
 * Respect to Google Java Style Guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class SpaceBattleGameSearchSpace implements SearchSpace{

  static int[][] values = {
      { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, // MISSILE_MAX_SPEED
      { 1, 2, 3, 4, 5, 6, 7, 8, 9}, // MISSILE_COOLDOWN
          {2, 4, 6, 8, 10}, //MISSILE_RADIUS
          {40, 60, 80, 100, 120, 140, 160}, //MISSILE_MAX_TTL
          {1, 2, 3, 4}, //GRID_SIZE
          {0, 1},
          {0, 1},
          {0, 1},
          {0, 1},
          {0, 1},
          {0, 1},
          {0, 1},
          {0, 1},
          {0, 1},
          {0, 1},
          {0, 1},
          {0, 1},
          {0, 1},
          {0, 1},
          {0, 1},
          {0, 1},
          {25, 50, 75, 100, 150, 200}, //BLACKHOLE_RADIUS
          {0, 1, 2, 3}, //BLACKHOLE_FORCE
          {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, //BLACKHOLE_PENALTY
          {0, 10, 20}, //SAFE_ZONE
          {10, 20, 30, 40, 50}, //BOMB_RADIUS
          {0, 1, 2}, // MISSILE_TYPE
          {400, 500, 600}, //RESOURCE_TTL
          {200,250,300}, //RESOURCE_COOLDOWN
          {0, 1, 2, 3, 4, 5} //ENEMY_ID
  };

  @Override
  public int nDims() {
    return values.length;
  }

  @Override
  public int nValues(int idxDim) {
    int dim = values[idxDim].length;
//    int dim = (GameDesign.bounds[idxDim][1] - GameDesign.bounds[idxDim][0])
//        / GameDesign.bounds[idxDim][2]
//        + 1;
    return dim;
  }

  public int getValue(int idxDim, int idx) {
    return values[idxDim][idx];
//    return GameDesign.bounds[idxDim][0] + idx * GameDesign.bounds[idxDim][2];
  }

  public int getDifferentRandomValue(int idxDim, int oldVal) {
    int idx;
    do {
      idx = new Random().nextInt(values[idxDim].length);
    } while (values[idxDim][idx] == oldVal);
    return values[idxDim][idx];
  }


  public int getRandomValue(int idxDim) {
    int idx = new Random().nextInt(values[idxDim].length);
    return values[idxDim][idx];
  }
  
  public int indexOf(int idxDim, int value)
  {
	  int[] a = values[idxDim];
	  
	  for(int i=0;i<a.length;i++)
		  if(a[i]==value)
			  return i;
	  
	  return -1;
  }


  public static int[] getParams(int idx) {
    int[] params = new int[values.length];
    int deno = 1;
    for (int i=0; i<params.length; i++) {
      deno = values[i].length;
      int k = idx % deno;
      params[i] = values[i][k];
      idx = (idx-k)/deno;
    }
    return params;
  }
}
