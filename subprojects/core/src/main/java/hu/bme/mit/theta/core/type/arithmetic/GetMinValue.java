package hu.bme.mit.theta.core.type.arithmetic;

import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.core.type.realtype.RealType;
import hu.bme.mit.theta.core.type.operator.PTAOperator;
import hu.bme.mit.theta.core.type.operator.PropertyOperator;

import java.util.List;

import static hu.bme.mit.theta.core.utils.TypeUtils.cast;

public class GetMinValue extends BinaryOperatorArthimetric<RealType> implements PTAOperator {

    protected GetMinValue(PropertyOperator operator, Expr<BoolType> pathProp) {
        super(operator, pathProp);
    }

    public static GetMinValue create(Expr<?> op1,Expr<?> op2){
        PropertyOperator operator = (PropertyOperator) op1;
        Expr<BoolType> pathprop=cast(op2,BoolType.getInstance());
        return new GetMinValue(operator,pathprop);
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
