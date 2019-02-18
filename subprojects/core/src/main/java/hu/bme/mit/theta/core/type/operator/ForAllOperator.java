package hu.bme.mit.theta.core.type.operator;

public class ForAllOperator extends PropertyOperator implements NondeterministicPropertyOperator {



    private static final int HASH_SEED = 3522;
    private static final String OPERATOR_LABEL = "A";
    private static final ForAllOperator INSTANCE=new ForAllOperator();

    private ForAllOperator(){}

    public static ForAllOperator getINSTANCE() {
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
