package hu.bme.mit.theta.mm.prop.operator;

public class ThereExistsOperator extends PropertyOperator implements NondeterministicPropertyOperator {


    private static final int HASH_SEED = 6264;
    private static final String OPERATOR_LABEL = "E";


    @Override
    public String getOperatorLabel() {
        return OPERATOR_LABEL;
    }

    @Override
    public int getHashSeed() {
        return HASH_SEED;
    }
}
