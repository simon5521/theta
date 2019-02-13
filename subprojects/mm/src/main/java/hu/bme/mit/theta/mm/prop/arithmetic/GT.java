package hu.bme.mit.theta.mm.prop.arithmetic;

import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.core.type.realtype.RealType;
import hu.bme.mit.theta.mm.prop.operator.PropertyOperator;

public class GT extends BinaryOperatorArthimetric<BoolType> {

    protected GT(PropertyOperator operator, Expr<BoolType> pathProp, LitExpr<RealType> opExpr) {
        super(operator, pathProp, opExpr);
    }

    @Override
    public BoolType getType() {
        return BoolType.getInstance();
    }
}
