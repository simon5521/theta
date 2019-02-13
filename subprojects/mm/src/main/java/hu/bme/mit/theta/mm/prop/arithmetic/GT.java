package hu.bme.mit.theta.mm.prop.arithmetic;

import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.core.type.realtype.RealType;
import hu.bme.mit.theta.mm.prop.operator.PropertyOperator;

import java.util.List;

public class GT extends TernaryOperatorArthimetric<BoolType> {

    protected GT(PropertyOperator operator, Expr<BoolType> pathProp, Expr<RealType> opExpr) {
        super(operator, pathProp, opExpr);
    }

    @Override
    public BoolType getType() {
        return BoolType.getInstance();
    }

    @Override
    public Expr<BoolType> withOps(List<? extends Expr<?>> ops) {
        throw new UnsupportedOperationException();
    }
}
