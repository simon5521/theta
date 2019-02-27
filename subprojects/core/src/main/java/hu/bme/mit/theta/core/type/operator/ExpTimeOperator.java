package hu.bme.mit.theta.core.type.operator;

public class ExpTimeOperator extends PropertyOperator {
    private static ExpTimeOperator ourInstance = new ExpTimeOperator();

    public static ExpTimeOperator getInstance() {
        return ourInstance;
    }

    private ExpTimeOperator() {
    }


    private static final int HASH_SEED = 6517;
    private static final String OPERATOR_LABEL = "ExpT";

    @Override
    public String getOperatorLabel() {
        return OPERATOR_LABEL;
    }

    @Override
    public int getHashSeed() {
        return HASH_SEED;
    }
}
