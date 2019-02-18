package hu.bme.mit.theta.mm.analysis.solver;

import hu.bme.mit.theta.mm.generator.MMPRISMWriter;
import hu.bme.mit.theta.mm.model.MarkovianModel;

import java.io.*;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public abstract class ExternalSolver implements MarkovSolver{


    private final String doubleResultPattern ="Result: [0-9]*\\.*[0-9]*";
    private final String boolResultPattern ="Result: [a-z]*";

    protected abstract List<String> generateCommand();


    protected abstract Scanner runSolver(List<String> commandLine) throws IOException;


    protected abstract void writeModellFile(String prizmModell) throws IOException;

    protected boolean findBoolResult(Scanner scanner){

        String rawResult=scanner.findWithinHorizon(boolResultPattern,20000);
        scanner=new Scanner(rawResult);
        scanner.useLocale(Locale.US);
        scanner.next();
        boolean res=scanner.nextBoolean();
        scanner.close();

        return res;

    }


    protected double findDoubleResult(Scanner scanner){

        String rawResult=scanner.findWithinHorizon(doubleResultPattern,20000);
        scanner=new Scanner(rawResult);
        scanner.useLocale(Locale.US);
        scanner.next();
        double res=scanner.nextDouble();
        scanner.close();

        return res;

    }


    protected double solveDouble(MarkovianModel model) throws IOException {
        MMPRISMWriter mmprismWriter=MMPRISMWriter.instance();
        writeModellFile(mmprismWriter.MarkovianModel2PRISM(model));
        List<String> commandLine = generateCommand();


        Scanner scanner=runSolver(commandLine);

        return findDoubleResult(scanner);

    }

    protected boolean solveBool(MarkovianModel model) throws IOException {
        MMPRISMWriter mmprismWriter=MMPRISMWriter.instance();
        writeModellFile(mmprismWriter.MarkovianModel2PRISM(model));

        List<String> commandLine=generateCommand();


        Scanner scanner=runSolver(commandLine);

        String resultString=scanner.toString();

        return findBoolResult(scanner);

    }




}
