package hu.bme.mit.theta.mm.model;

import hu.bme.mit.theta.core.decl.ParamDecl;
import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.stmt.AssignStmt;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.realtype.RealType;
import hu.bme.mit.theta.core.utils.ExprUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

public class DiscreteUpdate extends Update {

    public final Probability probability;

    protected DiscreteUpdate(Probability probability, Collection<AssignStmt<?>> updateExpr) {
        super(updateExpr);
        this.probability = probability;
    }

    public static Builder builder() {
        return new Builder();
    }


    @Override
    public LitExpr getRate(Valuation valuation) {
        return probability.probExpr.eval(valuation);
    }

    @Override
    public void collectParams(Collection<ParamDecl<?>> collection){
        collection.addAll(ExprUtils.getParams(probability.probExpr));
    }

    @Override
    public Set<ParamDecl<?>> getParams(){
        return ExprUtils.getParams(probability.probExpr);
    }



    public static final class Builder {
        private Probability probability;
        private Collection<AssignStmt<?>> updates;
        private boolean built;

        private Builder(){
            probability =null;
            updates=new HashSet<>();
            built=false;
        }


        public DiscreteUpdate build(){
            checkNotBuilt();
            built=true;
            return new DiscreteUpdate(probability,updates);
        }

        public void setProbability(final Expr<RealType> probExpr) {
            checkNotBuilt();
            this.probability =new Probability(probExpr);
        }

        public void addStmt(AssignStmt<?> update){
            checkNotBuilt();
            updates.add(update);
        }


        private void checkNotBuilt() {
            checkState(!built, "Update was already built.");
        }

    }


}
