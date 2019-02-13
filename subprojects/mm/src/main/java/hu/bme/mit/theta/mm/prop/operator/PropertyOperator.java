package hu.bme.mit.theta.mm.prop.operator;

import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.NullaryExpr;
import hu.bme.mit.theta.core.type.realtype.RealType;

import static com.google.common.base.Preconditions.checkArgument;

/*
 * for more information see:
 * https://www.prismmodelchecker.org/manual/PropertySpecification/SyntaxAndSemantics
 */
public abstract class PropertyOperator extends NullaryExpr<RealType> {


    @Override
    public LitExpr<RealType> eval(Valuation valuation){
        throw new UnsupportedOperationException();
    }

    @Override
    public RealType getType(){
        return RealType.getInstance();
    }

    public abstract String getOperatorLabel();

    public abstract int getHashSeed();

}
