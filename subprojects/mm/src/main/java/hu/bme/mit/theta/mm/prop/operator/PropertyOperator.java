package hu.bme.mit.theta.mm.prop.operator;

import hu.bme.mit.theta.core.type.realtype.RealType;

import static com.google.common.base.Preconditions.checkArgument;

/*
 * for more information see:
 * https://www.prismmodelchecker.org/manual/PropertySpecification/SyntaxAndSemantics
 */
public abstract class PropertyOperator extends RealType {




    public abstract String getOperatorLabel();

    public abstract int getHashSeed();

}
