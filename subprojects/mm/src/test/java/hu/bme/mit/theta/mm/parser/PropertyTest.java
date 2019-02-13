package hu.bme.mit.theta.mm.parser;


import hu.bme.mit.theta.core.type.booltype.TrueExpr;
import hu.bme.mit.theta.mm.generator.ExprPRISMWriter;
import hu.bme.mit.theta.mm.model.DiscreteTimeMarkovDecisionProcess;
import hu.bme.mit.theta.mm.prop.arithmetic.LT;
import hu.bme.mit.theta.mm.prop.operator.EventProbabilityOperator;
import hu.bme.mit.theta.mm.prop.templogic.FutureExpr;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class PropertyTest {


    @Test
    public void test(){

        //LT prop=new LT(new EventProbabilityOperator(),new FutureExpr(TrueExpr.getInstance()),);

        ExprPRISMWriter exprPRISMWriter=ExprPRISMWriter.instance();

    }
}
