package hu.bme.mit.theta.mm.analysis.solver;

import hu.bme.mit.theta.mm.generator.MMPRISMWriter;
import hu.bme.mit.theta.mm.model.MarkovianModel;

import java.io.*;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public abstract class ExternalSolver implements MarkovSolver{


    private final String doubleResultPattern;
    private final String boolResultPattern;

    protected ExternalSolver(String doubleResultPattern, String boolResultPattern) {
        this.doubleResultPattern = doubleResultPattern;
        this.boolResultPattern = boolResultPattern;
    }

    protected abstract List<String> generateCommand();


    protected abstract Scanner runSolver(List<String> commandLine) throws IOException;


    protected abstract void writeModellFile(String prizmModell) throws IOException;

    protected boolean findBoolResult(Scanner scanner){

        String rawResult=scanner.findWithinHorizon(boolResultPattern,200000000);

        if (rawResult==null) {
            StringBuilder stringBuilder=new StringBuilder();
            while (scanner.hasNextLine()) stringBuilder.append(scanner.nextLine());
            throw new UnsupportedOperationException("Pattern can not found: "+boolResultPattern+" | "+stringBuilder.toString());
        }
        Scanner scanner2=new Scanner(rawResult);
        scanner2.useLocale(Locale.US);
        scanner2=new Scanner(scanner2.findWithinHorizon("[a-z][a-z][a-z][a-z][a-z]",100));
        boolean res=scanner2.nextBoolean();
        scanner.close();
        scanner2.close();

        return res;

    }


    protected double findDoubleResult(Scanner scanner){


        String rawResult=scanner.findWithinHorizon(doubleResultPattern,200000000);
        if (rawResult==null) {
            StringBuilder stringBuilder=new StringBuilder();
            while (scanner.hasNextLine()) stringBuilder.append(scanner.nextLine());
            throw new UnsupportedOperationException("Pattern can not found: "+doubleResultPattern+" | "+stringBuilder.toString());
        }
        Scanner scanner2=new Scanner(rawResult);
        String result=scanner2.findWithinHorizon("[0-9]*\\.[0-9]*",1000);
        Scanner scanner3=new Scanner(result);
        scanner3.useLocale(Locale.US);
        double res=scanner3.nextDouble();
        scanner2.close();
        scanner.close();
        scanner3.close();

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
