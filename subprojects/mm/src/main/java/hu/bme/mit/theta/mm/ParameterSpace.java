package hu.bme.mit.theta.mm;

import hu.bme.mit.theta.core.type.Type;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ParameterSpace /*implements Type*/ {

    public ParameterSpaceState state=ParameterSpaceState.UNTESTED;

    public Map<String,Parameter> map = new HashMap<String,Parameter>();

    public ParameterSpace(){

    }

    public ParameterSpace(ParameterSpace parameterSpace){
        map.putAll(parameterSpace.map);
    }

    public Parameter getParameterByName(String name){
        return map.get(name);
    }

    public void addParameter(Parameter parameter){
        map.put(parameter.name,parameter);
    }

    ParameterSpace cutSpaceByParameterAndGetLow(String pameterName){
        Parameter cutParameter=map.get(pameterName);
        ParameterSpace cutLowParameterSpace=new ParameterSpace(this);
        cutLowParameterSpace.map.remove(pameterName);
        cutLowParameterSpace.map.put(cutParameter.lowHalf().name,cutParameter.lowHalf());
        return cutLowParameterSpace;
    }

    ParameterSpace cutSpaceByParameterAndGetUp(String pameterName){
        Parameter cutParameter=map.get(pameterName);
        ParameterSpace cutUpParameterSpace=new ParameterSpace(this);
        cutUpParameterSpace.map.remove(pameterName);
        cutUpParameterSpace.map.put(cutParameter.upHalf().name,cutParameter.upHalf());
        return cutUpParameterSpace;
    }




    public String toString(){
        StringBuilder stringBuilder=new StringBuilder("Parameterspace: \n");
        for (Map.Entry<String,Parameter> entry:map.entrySet()){
            stringBuilder.append(entry.getValue().name);
            stringBuilder.append(" : ");
            stringBuilder.append(entry.getValue().lowerLimit);
            stringBuilder.append("; ");
            stringBuilder.append(entry.getValue().upperLimit);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public double getJordanMeasure(){
        double measure=1;
        if(map.isEmpty()){
            measure=0;
        }
        for (Map.Entry<String,Parameter> entry:map.entrySet()){
            measure*=entry.getValue().upperLimit-entry.getValue().lowerLimit;
        }

        return measure;
    }

}
