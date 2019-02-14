package hu.bme.mit.theta.mm.analysis.solver;

import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.core.type.realtype.RealType;
import hu.bme.mit.theta.mm.dsl.MarkovianModel;
import hu.bme.mit.theta.mm.prop.arithmetic.OperatorArithmetic;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PRISMSolverWin extends ExternalSolver {    private final String modelChecker="c:\\Program Files\\prism-4.4\\bin\\prism.bat";

    private final String modelCheckerLocation="c:\\Program Files\\prism-4.4\\bin\\";
    private final String tempPropertyLocation="c:\\Users\\simon5521\\Desktop\\props.csl";
    private final String tempModelLocation="c:\\Users\\simon5521\\Desktop\\modell.pm";

    @Override
    protected Scanner runSolver(List<String> commandLine) throws IOException {

        Process process = new ProcessBuilder()
                .command(commandLine)
                .directory(new File(modelCheckerLocation))
                .start();

        return new Scanner(new BufferedInputStream(process.getInputStream()));

    }


    @Override
    protected void writeModellFile(String prizmModell) throws IOException {
        BufferedWriter fileWriter=new BufferedWriter(new FileWriter(tempModelLocation));
        fileWriter.write(prizmModell);
        fileWriter.close();
    }

    @Override
    protected List<String> generateCommand() {
        List<String> commandLine = new ArrayList<>(4);
        commandLine.add(modelCheckerLocation);
        commandLine.add(tempModelLocation);
        commandLine.add(tempPropertyLocation);
        return commandLine;
    }

    @Override
    protected void generateAndWriteProperty() {

    }

    @Override
    public boolean solveSingleDiscrete(MarkovianModel markovianModel, OperatorArithmetic<BoolType> singleProperty) {
        return false;
    }

    @Override
    public double solveSingleDouble(MarkovianModel markovianModel, OperatorArithmetic<RealType> singleProperty) {
        return 0;
    }
}
