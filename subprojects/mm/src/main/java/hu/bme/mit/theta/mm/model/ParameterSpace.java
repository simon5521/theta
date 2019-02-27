package hu.bme.mit.theta.mm.model;

import hu.bme.mit.theta.core.decl.ParamDecl;
import hu.bme.mit.theta.core.model.ImmutableValuation;
import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.realtype.RealLitExpr;
import hu.bme.mit.theta.core.type.realtype.RealType;

import java.util.Collection;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkState;

public class ParameterSpace /*implements Type*/ {

    public final Collection<ParamDecl<?>> parameters;


    public final Valuation lowerLimits;

    public final Valuation upperLimits;

    public ParameterSpace(Collection<ParamDecl<?>> parameters, Valuation lowerLimits, Valuation upperLimits) {
        this.parameters = parameters;
        this.lowerLimits = lowerLimits;
        this.upperLimits = upperLimits;
    }

    public LitExpr<?> getLimit(ParamDecl<?> paramDecl,ParameterDirection direction){

        if (!parameters.contains(paramDecl)){
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

    public double getExactLimit(ParamDecl<?> paramDecl,ParameterDirection direction){
        RealLitExpr limitExpr=(RealLitExpr) getLimit(paramDecl,direction);
        double limit=limitExpr.getValue();
        return limit;
    }

    public double getJordanMeasure(){
        if (parameters.isEmpty()) throw new UnsupportedOperationException("There is no parameter in parameter space -> Jordan measure does not exist");
        double measure=1;
        for (ParamDecl<?> param: parameters){
            if (param.getType().equals(RealType.getInstance()))
                measure*=(getExactLimit(param,ParameterDirection.UP)-getExactLimit(param,ParameterDirection.LOW));
        }

        return measure;
    }


    //the parameter cut supports only RealType parameters

    public ParameterSpace cutAndGetUpHalf(){
        Builder builder=builder();
        for (ParamDecl<?> param: parameters){
            if (param.getType().equals(RealType.getInstance()))
                builder.addParameter(
                        param,
                        RealLitExpr.of((getExactLimit(param,ParameterDirection.LOW)+getExactLimit(param,ParameterDirection.UP))/2),
                        RealLitExpr.of(getExactLimit(param,ParameterDirection.UP))
                );
        }

        return builder.build();
    }

    //the parameter cut supports only RealType parameters

    public ParameterSpace cutAndGetLowHalf(){
        Builder builder=builder();
        for (ParamDecl<?> param: parameters){
            if (param.getType().equals(RealType.getInstance()))
                builder.addParameter(
                        param,
                        RealLitExpr.of(getExactLimit(param,ParameterDirection.LOW)),
                        RealLitExpr.of((getExactLimit(param,ParameterDirection.LOW)+getExactLimit(param,ParameterDirection.UP))/2)
                );
        }

        return builder.build();
    }

    public ParameterSpace cutAndGetUpHalf(ParamDecl<?> cutParam){
        Builder builder=builder();
        for (ParamDecl<?> param: parameters){
            if (param.equals(cutParam)) {
                builder.addParameter(
                        param,
                        RealLitExpr.of((getExactLimit(param, ParameterDirection.LOW) + getExactLimit(param, ParameterDirection.UP)) / 2),
                        RealLitExpr.of(getExactLimit(param, ParameterDirection.UP))
                );
            }else {
                builder.addParameter(
                        param,
                        lowerLimits.eval(param).get(),
                        upperLimits.eval(param).get()
                );
            }
        }

        return builder.build();
    }
    public ParameterSpace cutAndGetLowHalf(ParamDecl<?> cutParam){
        Builder builder=builder();
        for (ParamDecl<?> param: parameters){
            if (param.equals(cutParam)) {
                builder.addParameter(
                        param,
                        RealLitExpr.of(getExactLimit(param, ParameterDirection.LOW)),
                        RealLitExpr.of((getExactLimit(param, ParameterDirection.LOW) + getExactLimit(param, ParameterDirection.UP)) / 2)
                );
            }else {
                builder.addParameter(
                        param,
                        lowerLimits.eval(param).get(),
                        upperLimits.eval(param).get()
                );
            }
        }

        return builder.build();
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
