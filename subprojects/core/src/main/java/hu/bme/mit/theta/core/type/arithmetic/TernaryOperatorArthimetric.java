package hu.bme.mit.theta.core.type.arithmetic;

import com.google.common.collect.ImmutableList;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.Type;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.core.type.realtype.RealType;
import hu.bme.mit.theta.core.type.operator.PropertyOperator;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public abstract class TernaryOperatorArthimetric<ReturnType extends Type> extends OperatorArithmetic<ReturnType>  {

    protected TernaryOperatorArthimetric(PropertyOperator operator, Expr<BoolType> pathProp, Expr<RealType> opExpr) {
        super(operator, pathProp);
        this.opExpr = opExpr;
    }

    public final Expr<RealType> opExpr;

    @Override
    public int getArity(){
        return 3;
    }

    @Override
    public final List<Expr<?>> getOps() {
        return ImmutableList.of(operator, opExpr, pathProp);
    }


}
