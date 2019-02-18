package hu.bme.mit.theta.mm.parser;

import hu.bme.mit.theta.common.parser.LispLexer;
import hu.bme.mit.theta.common.parser.LispParser;
import hu.bme.mit.theta.common.parser.SExpr;
import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.parser.Env;
import hu.bme.mit.theta.mm.prop.Property;

import java.io.Reader;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

public class PropertyParser {

    private final LispParser lispParser;
    private final PropertyInterpreter propertyInterpreter;


    public PropertyParser(Reader reader, Collection<VarDecl<?>> variables){
        checkNotNull(reader);
        final LispLexer lispLexer=new LispLexer(reader);
        lispParser=new LispParser(lispLexer);
        final Env env=new Env();
        propertyInterpreter =new PropertyInterpreter(env,variables);
    }

    public Property property(){
        SExpr sExpr=lispParser.sexpr();
        Property property= propertyInterpreter.property(sExpr);
        return property;
    }




}
