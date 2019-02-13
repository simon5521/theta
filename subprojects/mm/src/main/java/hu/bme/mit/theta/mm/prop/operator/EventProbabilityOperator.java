package hu.bme.mit.theta.mm.prop.operator;

public class EventProbabilityOperator extends PropertyOperator {

    private static final int HASH_SEED = 3636;
    private static final String OPERATOR_LABEL = "P";

    @Override
    public String getOperatorLabel() {
        return OPERATOR_LABEL;
    }

    @Override
    public int getHashSeed() {
        return HASH_SEED;
    }
}
