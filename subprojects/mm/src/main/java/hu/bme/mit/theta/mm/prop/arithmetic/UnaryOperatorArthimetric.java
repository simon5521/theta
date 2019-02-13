package hu.bme.mit.theta.mm.prop.arithmetic;

import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.Type;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.mm.prop.operator.PropertyOperator;

public abstract class UnaryOperatorArthimetric<ReturnType extends Type> extends OperatorArithmetic<ReturnType> {
    protected UnaryOperatorArthimetric(PropertyOperator operator, Expr<BoolType> pathProp) {
        super(operator, pathProp);
    }
}
