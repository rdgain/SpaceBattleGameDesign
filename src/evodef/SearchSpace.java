package evodef;

/**
 * Created by sml on 16/08/2016.
 *
 *  This models a search space where there is a fixed number of dimensions
 *  but each dimension may have a different cardinality (i.e. a different number of possible values)
 *
 */
public interface SearchSpace {
    // number of dimensions
    int nDims();
    // number of possile values in the ith dimension
    int nValues(int i);
    public int getValue(int idxDim, int idx) ;
    public int getRandomValue(int idxDim);
    public int getDifferentRandomValue(int idxDim, int oldVal);
    public int indexOf(int idxDim, int value);
}
