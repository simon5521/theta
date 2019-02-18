package hu.bme.mit.theta.core.type.operator;

public class RewardOperator extends PropertyOperator {



    private static final int HASH_SEED = 6264;
    private static final String OPERATOR_LABEL = "R";
    private static final RewardOperator INSTANCE=new RewardOperator();

    private RewardOperator(){}

    public static RewardOperator getINSTANCE() {
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
