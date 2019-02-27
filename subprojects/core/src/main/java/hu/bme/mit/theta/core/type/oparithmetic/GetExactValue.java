package hu.bme.mit.theta.core.type.oparithmetic;

import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.core.type.realtype.RealType;
import hu.bme.mit.theta.core.type.operator.PropertyOperator;

import java.util.List;

import static hu.bme.mit.theta.core.utils.TypeUtils.cast;

public class GetExactValue extends BinaryOperatorArthimetric<RealType> {

    private static final int HASH_SEED = 2345;
    private static final String OPERATOR_LABEL = "=?";

    public static GetExactValue create(Expr<?> op1,Expr<?> op2){
        PropertyOperator operator = (PropertyOperator) op1;
        Expr<BoolType> pathprop=cast(op2,BoolType.getInstance());
        return new GetExactValue(operator,pathprop);
    }

    protected GetExactValue(PropertyOperator operator, Expr<BoolType> pathProp) {
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
