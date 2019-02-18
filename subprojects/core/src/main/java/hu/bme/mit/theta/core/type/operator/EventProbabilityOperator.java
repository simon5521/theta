package hu.bme.mit.theta.core.type.operator;

public class EventProbabilityOperator extends PropertyOperator {

    private static final int HASH_SEED = 3636;
    private static final String OPERATOR_LABEL = "P";
    private static final EventProbabilityOperator INSTANCE=new EventProbabilityOperator();

    private EventProbabilityOperator(){

    }

    public static EventProbabilityOperator getINSTANCE() {
        return INSTANCE;
    }

    public static EventProbabilityOperator create(){
        return new EventProbabilityOperator();
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
