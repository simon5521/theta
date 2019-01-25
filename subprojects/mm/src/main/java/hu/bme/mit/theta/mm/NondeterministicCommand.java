package hu.bme.mit.theta.mm;

import hu.bme.mit.theta.core.stmt.AssignStmt;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.core.type.realtype.RealType;

import java.util.Collection;
import java.util.List;

//I may not will use it in the future
public class NondeterministicCommand extends Command {

    public final int actionNumber;


    //this array contains the numeric rates of the updates for each actions
    //this array should be evaluated in each iteration cycle
    public final LitExpr<RealType>[][] rates; //first dimension shows the target (update), second dimension shows the action




    public NondeterministicCommand(List<Update> updates, Expr<BoolType> guard, Collection<AssignStmt<?>> action,LitExpr<RealType>[][] rates) {
        super(updates, guard, action);
        actionNumber=pow2(super.parameterNumber);
        this.rates=rates;
    }


    private int pow2(int n){
        int r=1;
        for (int i=0;i<n;i++){
            r*=2;
        }
        return r;
    }
}
