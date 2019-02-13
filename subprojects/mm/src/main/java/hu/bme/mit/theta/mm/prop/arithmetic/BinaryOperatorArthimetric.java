package hu.bme.mit.theta.mm.prop.arithmetic;

import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.Type;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.core.type.realtype.RealType;
import hu.bme.mit.theta.mm.prop.operator.PropertyOperator;

public abstract class BinaryOperatorArthimetric<ReturnType extends Type> extends OperatorArithmetic<ReturnType>  {

    protected BinaryOperatorArthimetric(PropertyOperator operator, Expr<BoolType> pathProp, LitExpr<RealType> opExpr) {
        super(operator, pathProp);
        this.opExpr = opExpr;
    }

    public final LitExpr<RealType> opExpr;

}
