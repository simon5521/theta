package hu.bme.mit.theta.mm.prop.operator;

import hu.bme.mit.theta.mm.prop.operator.NondeterministicPropertyOperator;
import hu.bme.mit.theta.mm.prop.operator.PropertyOperator;

public class ForAllOperator extends PropertyOperator implements NondeterministicPropertyOperator {



    private static final int HASH_SEED = 3522;
    private static final String OPERATOR_LABEL = "A";


    @Override
    public String getOperatorLabel() {
        return OPERATOR_LABEL;
    }

    @Override
    public int getHashSeed() {
        return HASH_SEED;
    }
}
