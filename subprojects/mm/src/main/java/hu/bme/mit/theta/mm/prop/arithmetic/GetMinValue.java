package hu.bme.mit.theta.mm.prop.arithmetic;

import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.core.type.realtype.RealType;
import hu.bme.mit.theta.mm.prop.operator.PTAOperator;
import hu.bme.mit.theta.mm.prop.operator.PropertyOperator;

import java.util.List;

public class GetMinValue extends BinaryOperatorArthimetric<RealType> implements PTAOperator {

    protected GetMinValue(PropertyOperator operator, Expr<BoolType> pathProp) {
        super(operator, pathProp);
    }

    @Override
    public RealType getType() {
        return RealType.getInstance();
    }


    @Override
    public Expr<RealType> withOps(List<? extends Expr<?>> ops) {
        return new GetExactValue( (PropertyOperator) ops.get(0),(Expr<BoolType>) ops.get(1) );
    }

}
