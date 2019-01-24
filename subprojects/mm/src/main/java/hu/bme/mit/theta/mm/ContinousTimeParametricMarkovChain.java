package hu.bme.mit.theta.mm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContinousTimeParametricMarkovChain {

    private ParameterSpace parameterSpace;

    public final double uniformExitRate=25;

    public final double uniformExitExpectedTime=1/uniformExitRate;

    public final Integer startingLocation;

    public final Integer locationNumber;

    public final Integer targetLocation;


    public Command[] commands;

    public ContinousTimeParametricMarkovChain(int startingLocation, int locationNumber, int targetLocation){
        this.startingLocation = startingLocation;
        this.locationNumber = locationNumber;
        commands =new Command[locationNumber];
        if(targetLocation<=locationNumber)
            this.targetLocation = targetLocation;
        else {
            this.targetLocation = -1;
            System.out.println("there is an error with the target location");
        }
    }


    public ContinousTimeParametricMarkovChain copy(){
        ContinousTimeParametricMarkovChain continousTimeParametricMarkovChain =new ContinousTimeParametricMarkovChain(startingLocation, locationNumber, targetLocation);
        continousTimeParametricMarkovChain.commands =this.commands;
        continousTimeParametricMarkovChain.parameterSpace=this.parameterSpace;
        return continousTimeParametricMarkovChain;
    }

    public void setParameterSpace(ParameterSpace parameterSpace){

        this.parameterSpace = parameterSpace;

        for(Command command : commands){
            command.setParameterSpace(parameterSpace);
        }

    }

    public ParameterSpace getParameterSpace(){
        return parameterSpace;
    }




    private void readParameters(){
        parameterSpace=new ParameterSpace();

    }

    private void readLocations(){
        List<Command> tmpCommands =new ArrayList<>();
        // #todo
        commands = tmpCommands.toArray(new Command[locationNumber]);

    }

}
