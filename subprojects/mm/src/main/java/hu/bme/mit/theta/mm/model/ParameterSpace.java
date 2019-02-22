package hu.bme.mit.theta.mm.model;

import hu.bme.mit.theta.core.decl.ParamDecl;
import hu.bme.mit.theta.core.model.ImmutableValuation;
import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.type.LitExpr;

import java.util.Collection;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkState;

public class ParameterSpace /*implements Type*/ {

    public final Collection<ParamDecl<?>> paramDecls;


    public final Valuation lowerLimits;

    public final Valuation upperLimits;

    public ParameterSpace(Collection<ParamDecl<?>> paramDecls, Valuation lowerLimits, Valuation upperLimits) {
        this.paramDecls = paramDecls;
        this.lowerLimits = lowerLimits;
        this.upperLimits = upperLimits;
    }

    public LitExpr<?> getLimit(ParamDecl<?> paramDecl,ParameterDirection direction){

        if (!paramDecls.contains(paramDecl)){
            throw new UnsupportedOperationException("Can not found the parameter in parameterspace.");
        }

        if (direction == ParameterDirection.LOW){
            return lowerLimits.eval(paramDecl).get();
        } else if (direction==ParameterDirection.UP){
            return upperLimits.eval(paramDecl).get();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static ParameterSpace.Builder builder(){
        return new Builder();
    }

    public static final class Builder{

        private Collection<ParamDecl<?>> paramDecls;

        private ImmutableValuation.Builder lowerLimits;

        private ImmutableValuation.Builder upperLimits;

        private boolean built;

        private Builder(){
            lowerLimits=ImmutableValuation.builder();
            upperLimits=ImmutableValuation.builder();
            built=false;
            paramDecls=new HashSet<>();
        }

        public void addParameter(ParamDecl<?> paramDecl, LitExpr<?> lowerLimit,LitExpr<?> upperLimit){
            checkNotBuilt();
            lowerLimits.put(paramDecl,lowerLimit);
            upperLimits.put(paramDecl,upperLimit);
            paramDecls.add(paramDecl);
        }

        public ParameterSpace build(){
            checkNotBuilt();
            built=true;
            return new ParameterSpace(paramDecls, lowerLimits.build(),upperLimits.build());
        }

        private void checkNotBuilt() {
            checkState(!built, "A parameter space was already built.");
        }




    }





}
