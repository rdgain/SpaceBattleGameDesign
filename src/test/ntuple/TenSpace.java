package test.ntuple;

import evodef.SearchSpace;

/**
 * Created by sml on 14/11/2016.
 */ // this gives base 10 search space for convenient checking of N-Tuple addressing mechanism


public class TenSpace implements SearchSpace {

    int nValues, nDims;

    public TenSpace(int nDims) {

        this(nDims, 5);
    }

    public TenSpace(int nDims, int nValues) {
        this.nDims = nDims;
        this.nValues = nValues;
    }

    @Override
    public int nDims() {
        return nDims;
    }

    @Override
    public int nValues(int i) {
        return nValues;
    }

    @Override
    public int getValue(int idxDim, int idx) {
        return 0;
    }

    @Override
    public int getRandomValue(int idxDim) {
        return 0;
    }

    @Override
    public int getDifferentRandomValue(int idxDim, int oldVal) {
        return 0;
    }

    @Override
    public int indexOf(int idxDim, int value) {
        return 0;
    }
}
