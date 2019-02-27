package hu.bme.mit.theta.core.type.operator;

public class TimeOperator extends PropertyOperator {
    private static TimeOperator ourInstance = new TimeOperator();

    public static TimeOperator getInstance() {
        return ourInstance;
    }

    private TimeOperator() {
    }


    private static final int HASH_SEED = 2374;
    private static final String OPERATOR_LABEL = "T";

    @Override
    public String getOperatorLabel() {
        return OPERATOR_LABEL;
    }

    @Override
    public int getHashSeed() {
        return HASH_SEED;
    }
}
