package hu.bme.mit.theta.mm;

import java.util.List;

//I may not will use it in the future
public class NondeterministicCommand extends Command {

    public final int actionNumber;


    //this array contains the numeric rates of the updates for each actions
    //this array should be evaluated in each iteration cycle
    public double[][] rates; //first dimension shows the target (update), second dimension shows the action


    public NondeterministicCommand(ParameterSpace parameterSpace, int locationNumber, List<Update> updates, String guard, String action, double [][] rates) {
        super(parameterSpace, locationNumber, updates, guard, action);
        actionNumber=pow2(parameterNumber);
        this.rates=new double[updates.size()][actionNumber];
    }

    private int pow2(int n){
        int r=1;
        for (int i=0;i<n;i++){
            r*=2;
        }
        return r;
    }
}
