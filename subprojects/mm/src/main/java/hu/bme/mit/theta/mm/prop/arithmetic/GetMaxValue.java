package hu.bme.mit.theta.mm.prop.arithmetic;

import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.core.type.realtype.RealType;
import hu.bme.mit.theta.mm.prop.operator.PTAOperator;
import hu.bme.mit.theta.mm.prop.operator.PropertyOperator;

public class GetMaxValue extends UnaryOperatorArthimetric<RealType> implements PTAOperator {
    protected GetMaxValue(PropertyOperator operator, Expr<BoolType> pathProp) {
        super(operator, pathProp);
    }

    @Override
    public RealType getType() {
        return RealType.getInstance();
    }
}
