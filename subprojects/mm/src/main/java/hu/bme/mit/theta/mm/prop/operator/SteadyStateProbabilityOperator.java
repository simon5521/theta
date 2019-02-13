package hu.bme.mit.theta.mm.prop.operator;

public class SteadyStateProbabilityOperator extends PropertyOperator {


    private static final int HASH_SEED = 8465;
    private static final String OPERATOR_LABEL = "S";

    @Override
    public String getOperatorLabel() {
        return OPERATOR_LABEL;
    }

    @Override
    public int getHashSeed() {
        return HASH_SEED;
    }
}
