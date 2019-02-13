package hu.bme.mit.theta.mm.prop.arithmetic;

import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.Type;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.mm.prop.operator.PropertyOperator;
import hu.bme.mit.theta.mm.prop.templogic.TemporalLogicExpr;

import static com.google.common.base.Preconditions.checkArgument;

public abstract class OperatorArithmetic<ArithmeticType extends Type> {

    public final PropertyOperator operator;

    public final Expr<BoolType> pathProp;

    protected OperatorArithmetic(PropertyOperator operator, Expr<BoolType> pathProp) {
        checkArgument(pathProp instanceof TemporalLogicExpr,"Only temporal logic expression can be used in property.");
        this.operator = operator;
        this.pathProp = pathProp;
    }

    public abstract ArithmeticType getType();

}
