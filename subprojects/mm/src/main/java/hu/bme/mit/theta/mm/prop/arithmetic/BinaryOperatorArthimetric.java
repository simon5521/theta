package hu.bme.mit.theta.mm.prop.arithmetic;

import com.google.common.collect.ImmutableList;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.Type;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.mm.prop.operator.PropertyOperator;

import java.util.List;

public abstract class BinaryOperatorArthimetric<ReturnType extends Type> extends OperatorArithmetic<ReturnType> {
    protected BinaryOperatorArthimetric(PropertyOperator operator, Expr<BoolType> pathProp) {
        super(operator, pathProp);
    }

    @Override
    public int getArity(){
        return 2;
    }

    @Override
    public final List<Expr<?>> getOps() {
        return ImmutableList.of(operator, pathProp);
    }


}
