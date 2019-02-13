package hu.bme.mit.theta.mm.prop;

import hu.bme.mit.theta.core.decl.ConstDecl;
import hu.bme.mit.theta.mm.prop.arithmetic.OperatorArithmetic;

import java.util.Collection;

public class Property {

    public final Collection<ConstDecl<?>> constans;
    public final Collection<MultiObjective> multiObjectives;
    public final Collection<OperatorArithmetic<?>> objectives;

    public Property(Collection<ConstDecl<?>> constans, Collection<MultiObjective> multiObjectives, Collection<OperatorArithmetic<?>> objectives) {
        this.constans = constans;
        this.multiObjectives = multiObjectives;
        this.objectives = objectives;
    }
}
