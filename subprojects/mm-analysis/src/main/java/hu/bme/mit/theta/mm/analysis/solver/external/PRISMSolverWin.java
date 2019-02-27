package hu.bme.mit.theta.mm.analysis.solver.external;

import hu.bme.mit.theta.mm.generator.MMPRISMWriter;
import hu.bme.mit.theta.mm.model.MarkovianModel;
import hu.bme.mit.theta.mm.model.Reward;
import hu.bme.mit.theta.mm.prop.Property;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;


@SuppressWarnings("untested")
public class PRISMSolverWin extends ExternalSolver {


    private final String modelChecker="c:\\Program Files\\prism-4.4\\bin\\prism.bat";

    private final String modelCheckerLocation="c:\\Program Files\\prism-4.4\\bin\\";
    private final String tempPropertyLocation="c:\\Users\\simon5521\\Desktop\\props.csl";
    private final String tempModelLocation="c:\\Users\\simon5521\\Desktop\\modell.pm";

    public PRISMSolverWin() {
        super("Result: [0-9]*\\.*[0-9]*", "Result: [a-z]*");
    }

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
        commandLine.add(modelChecker);
        commandLine.add(tempModelLocation);
        commandLine.add(tempPropertyLocation);
        return commandLine;
    }


    protected void writeProperty(Property property) throws IOException {
        MMPRISMWriter mmprismWriter = MMPRISMWriter.instance();
        BufferedWriter fileWriter=new BufferedWriter(new FileWriter(tempPropertyLocation));
        fileWriter.write(mmprismWriter.Property2PRISM(property));
        fileWriter.close();
    }


    @Override
    public boolean solveBinSingle(MarkovianModel markovianModel, Property property) {
        boolean check=false;
        try {
            writeProperty(property);
            check=solveBool(markovianModel);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("There is a fatal problem with the Ountput files;");
        }
        return check;
    }

    @Override
    public double solveDoubleSingle(MarkovianModel markovianModel, Property property) {
        double check=0;
        try {
            writeProperty(property);
            check=solveDouble(markovianModel);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("There is a fatal problem with the Ountput files.");
        }
        return check;
    }

    @Override
    public boolean solveBinSingle(MarkovianModel markovianModel, Collection<Reward> rewards, Property property) {
        boolean check=false;
        try {
            writeProperty(property);
            check=solveBool(markovianModel,rewards);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("There is a fatal problem with the Ountput files;");
        }
        return check;
    }

    @Override
    public double solveDoubleSingle(MarkovianModel markovianModel, Collection<Reward> rewards, Property property) {
        double check=0;
        try {
            writeProperty(property);
            check=solveDouble(markovianModel,rewards);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("There is a fatal problem with the Ountput files.");
        }
        return check;
    }
}
