package hu.bme.mit.theta.core.type.arithmetic;

import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.core.type.realtype.RealType;
import hu.bme.mit.theta.core.type.operator.PropertyOperator;

import java.util.List;

import static hu.bme.mit.theta.core.type.booltype.BoolExprs.Bool;
import static hu.bme.mit.theta.core.type.realtype.RealExprs.Real;
import static hu.bme.mit.theta.core.utils.TypeUtils.cast;

public class GT extends TernaryOperatorArthimetric<BoolType> {

    protected GT(PropertyOperator operator, Expr<BoolType> pathProp, Expr<RealType> opExpr) {
        super(operator, pathProp, opExpr);
    }


    public static GT create(Expr<?> op1,Expr<?> op2,Expr<?> op3){
        PropertyOperator operator = (PropertyOperator) op1;
        Expr<BoolType> pathprop=cast(op2,Bool());
        Expr<RealType> opExpr=cast(op3,Real());
        return new GT(operator,pathprop,opExpr);
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
