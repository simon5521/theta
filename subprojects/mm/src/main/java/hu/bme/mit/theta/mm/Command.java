package hu.bme.mit.theta.mm;

import java.util.HashSet;
import java.util.List;

public class Command {

    public ParameterSpace localParameters=new ParameterSpace();

    private ParameterSpace parameterSpace;

    public List<Update> updates;


    public final int locationNumber;

    public final int parameterNumber;

    public static final String note="Location";

    public final String guard;

    public final String action;



    public Command(ParameterSpace parameterSpace, int locationNumber, List<Update> updates, String guard, String action){
        this.parameterSpace = parameterSpace;
        this.locationNumber=locationNumber;
        this.updates=updates;
        this.guard = guard;
        this.action = action;
        collectLocalParameters();
        parameterNumber=localParameters.map.size();

    }

    public void setParameterSpace(ParameterSpace parameterSpace){

        this.parameterSpace = parameterSpace;

        for(Update update : updates){
            if(update !=null) {
                update.setParameterSpace(parameterSpace);
            }
        }

    }

    private void collectLocalParameters(){
        localParameters.map.clear();
        for(Update update: updates) {
            localParameters.map.putAll(update.localParameters.map);
        }
    }



}
