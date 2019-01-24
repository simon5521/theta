package hu.bme.mit.theta.mm;

import hu.bme.mit.theta.core.type.functype.FuncExprs;
import hu.bme.mit.theta.core.type.functype.FuncType;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Update {

    private ParameterSpace parameterSpace;

    public final String rate;

    public FuncExprs rateExpr;

    public final String target;


    Rate numRate;

    public final ParameterSpace localParameters;

    public Update(ParameterSpace parameterSpace, String rate, String target){
        this.parameterSpace = parameterSpace;
        this.target = target;
        localParameters = new ParameterSpace();
        this.rate = rate;
        searchParameters();
    }


    public Rate getRate(){
        FuncType<ParameterSpace,Rate> func=rateExpr.Func(localParameters,numRate);
        return numRate;
    }


    private void searchParameters(){
        localParameters.map.clear();
        for (Map.Entry<String,Parameter> entry:
         parameterSpace.map.entrySet()    ) {
            if(rate.contains(entry.getKey())){
                localParameters.map.put(entry.getValue().name,entry.getValue());
            }
        }
    }



    public void setParameterSpace(ParameterSpace parameterSpace){

        this.parameterSpace=parameterSpace;
        searchParameters();
    }


}
