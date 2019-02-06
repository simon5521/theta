package hu.bme.mit.theta.mm.parser;

import static com.google.common.base.Preconditions.checkNotNull;

import hu.bme.mit.theta.common.parser.LispLexer;
import hu.bme.mit.theta.common.parser.LispParser;
import hu.bme.mit.theta.common.parser.SExpr;
import hu.bme.mit.theta.core.parser.Env;
import hu.bme.mit.theta.mm.data.ParametricContinousTimeMarkovChain;

import java.io.Reader;

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

    public ParametricContinousTimeMarkovChain markovianModel(){
        SExpr sExpr=lispParser.sexpr();
        ParametricContinousTimeMarkovChain parametricContinousTimeMarkovChain=markovianModelInterpreter.parametricContiniuousTimeMarkovChain(sExpr);
        return parametricContinousTimeMarkovChain;
    }


}
