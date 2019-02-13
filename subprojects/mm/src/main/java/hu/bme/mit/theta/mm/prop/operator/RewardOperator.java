package hu.bme.mit.theta.mm.prop.operator;

public class RewardOperator extends PropertyOperator {



    private static final int HASH_SEED = 6264;
    private static final String OPERATOR_LABEL = "R";


    @Override
    public String getOperatorLabel() {
        return OPERATOR_LABEL;
    }

    @Override
    public int getHashSeed() {
        return HASH_SEED;
    }
}
