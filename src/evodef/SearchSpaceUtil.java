package evodef;

import test.SpaceBattleGameSearchSpace;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by simonmarklucas on 24/10/2016.
 */
public class SearchSpaceUtil {

    public static void main(String[] args) {
        // test searh space enumeration
//        SearchSpace space = new TenSpace(10, 2);
//
//        // create a search space with a variable niumber of values in each dimension
//        int[] nValues = {2, 10, 5};
//        space = new VariSpace(nValues);
//
//        for (int i=0; i<size(space); i++) {
//            int[] x = nthPoint(space, i);
//            int ix = indexOf(space, x);
//            System.out.println(i + "\t " + ix + "\t " + Arrays.toString(nthPoint(space, i)));
//
//        }
    }

    public static double size(SearchSpace space) {
        double size = 1;
        for (int i=0; i<space.nDims(); i++) {
            size *= space.nValues(i);
        }
        return size;
    }

    static Random random = new Random();

    public static int[] randomPoint(SearchSpace space) {

        int[] p = new int[space.nDims()];
        for (int i=0; i<p.length; i++) {
            p[i] = random.nextInt(space.nValues(i));
        }
        return p;
    }

    public static int[] nthPoint(SearchSpace space, int ix) {
        int[] p = new int[space.nDims()];
        // start of at the last dimension in order for points
        // to be listed in conventional order
        for (int i=p.length-1; i>=0; i--) {
            p[i] = ix % space.nValues(i);
            ix /= space.nValues(i);
        }
        return p;

    }

    public static int indexOf(SearchSpace space, int[] p) {

        int fac = 1;
        int tot = 0;
        for (int i=p.length-1; i>=0; i--) {
            tot += p[i] * fac;
            fac *= space.nValues(i);
        }
        return tot;

    }


}
