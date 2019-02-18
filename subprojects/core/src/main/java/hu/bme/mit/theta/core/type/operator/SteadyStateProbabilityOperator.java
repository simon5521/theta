package hu.bme.mit.theta.core.type.operator;

public class SteadyStateProbabilityOperator extends PropertyOperator {


    private static final int HASH_SEED = 8465;
    private static final String OPERATOR_LABEL = "S";
    private static final SteadyStateProbabilityOperator INSTANCE = new SteadyStateProbabilityOperator();

    private SteadyStateProbabilityOperator(){}

    public static SteadyStateProbabilityOperator getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public String getOperatorLabel() {
        return OPERATOR_LABEL;
    }

    @Override
    public int getHashSeed() {
        return HASH_SEED;
    }
}
