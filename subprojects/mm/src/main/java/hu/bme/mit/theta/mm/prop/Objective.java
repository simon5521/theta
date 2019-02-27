package hu.bme.mit.theta.mm.prop;

import hu.bme.mit.theta.core.type.oparithmetic.OperatorArithmetic;

public class Objective {

    public final OperatorArithmetic<?> operatorArithmetic;

    public final String name;

    public Objective(OperatorArithmetic<?> operatorArithmetic, String name) {
        this.operatorArithmetic = operatorArithmetic;
        this.name = name;
    }
}
