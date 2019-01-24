package hu.bme.mit.theta.mm;

import java.util.ArrayList;
import java.util.List;

public class ContinuousTimeParametricMarkovDecisionProcess{

    public int actionNumber;

    public final double uniformExitRate;

    public final double uniformExitExpectedTime;

    public final Integer startingLocation;

    public final Integer locationNumber;

    public final Integer targetLocation;

    public List<NondeterministicCommand> commands=new ArrayList<>();

    public ContinuousTimeParametricMarkovDecisionProcess(double uniformExitRate, int startingLocation, int locationNumber, int targetLocation){
        this.uniformExitRate = uniformExitRate;
        uniformExitExpectedTime=1/uniformExitRate;
        this.startingLocation = startingLocation;
        this.locationNumber = locationNumber;
        if(targetLocation<=locationNumber)
            this.targetLocation = targetLocation;
        else {
            this.targetLocation = -1;
            System.out.println("there is an error with the target location");
        }
    }

    public void calculateActionNumber(){
        int maxActionNumber=0;
        for (NondeterministicCommand command:commands){

            if(command.actionNumber>maxActionNumber){
                maxActionNumber= command.actionNumber;
            }

        }
        actionNumber=maxActionNumber;
    }


}
