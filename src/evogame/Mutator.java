package evogame;

import evodef.SearchSpace;

import java.util.Random;

/**
 * Created by sml on 17/01/2017.
 */
public class Mutator {

    public double pointProb = 0.5;
    static Random random = new Random();

    SearchSpace searchSpace;

    public Mutator(SearchSpace searchSpace) {
        this.searchSpace = searchSpace;
    }

    public int[] randMut(int[] v) {
        // note: the algorithm ensures that at least one of the bits is different in the returned array
        int n = v.length;
        int[] x = new int[n];
        // pointwise probability of additional mutations
        double mutProb = pointProb / n;
        // choose element of vector to mutate
        int ix = random.nextInt(n);
        // copy all the values fauthfully apart from the chosen one
        for (int i=0; i<n; i++) {
            if (i == ix || random.nextDouble() < mutProb) {
                x[i] = searchSpace.getValue(i, mutateValue(v[i], searchSpace.nValues(i)));
            } else {
                x[i] = v[i];
            }
            
         //   System.out.print(x[i]+" ");
        }
        return x;
    }

    int mutateValue(int cur, int nPossible) {
        // the range is nPossible-1, since we
        // selecting the current value is not allowed
        // therefore we add 1 if the randomly chosen
        // value is greater than or equal to the current value
        int rx = random.nextInt(nPossible-1);
        return rx >= cur ? rx+1 : rx;
    }
}
