package hu.bme.mit.theta.mm.parser;

import static com.google.common.base.Preconditions.checkNotNull;

import hu.bme.mit.theta.common.parser.LispLexer;
import hu.bme.mit.theta.common.parser.LispParser;
import hu.bme.mit.theta.common.parser.SExpr;
import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.parser.Env;
import hu.bme.mit.theta.mm.model.DiscreteTimeMarkovDecisionProcess;
import hu.bme.mit.theta.mm.model.ParameterSpace;
import hu.bme.mit.theta.mm.model.ParametricContinousTimeMarkovChain;

import java.io.Reader;
import java.util.Collection;

public class MarkovianModelParser {

    private final LispParser lispParser;
    private final MarkovianModelInterpreter markovianModelInterpreter;


    public MarkovianModelParser(Reader reader){
        checkNotNull(reader);
        final LispLexer lispLexer=new LispLexer(reader);
        lispParser=new LispParser(lispLexer);
        final Env env=new Env();
        markovianModelInterpreter=new MarkovianModelInterpreter(env);
    }




    //must be called after the model is read
    public ParameterSpace parameterspace(){
        return markovianModelInterpreter.getParameterSpace();
    }

    public ParametricContinousTimeMarkovChain pCTMC(){
        SExpr sExpr=lispParser.sexpr();
        ParametricContinousTimeMarkovChain parametricContinousTimeMarkovChain=markovianModelInterpreter.parametricContiniuousTimeMarkovChain(sExpr);
        return parametricContinousTimeMarkovChain;
    }


    public DiscreteTimeMarkovDecisionProcess DTMDP(){
        SExpr sExpr=lispParser.sexpr();
        DiscreteTimeMarkovDecisionProcess discreteTimeMarkovDecisionProcess=markovianModelInterpreter.discreteTimeMarkovDecisionProcess(sExpr);
        return discreteTimeMarkovDecisionProcess;
    }


    public Collection<Valuation> NondetInitialStates(){
        SExpr sExpr=lispParser.sexpr();
        Collection<Valuation> initStates=markovianModelInterpreter.multipleInitialStates(sExpr);
        return initStates;
    }

}
