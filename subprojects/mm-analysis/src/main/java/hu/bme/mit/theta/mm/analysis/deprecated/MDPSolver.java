package hu.bme.mit.theta.mm.analysis.deprecated;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MDPSolver {

    private final String prizmmodellcheckerLocation="c:\\Program Files\\prism-4.4\\bin\\prism.bat";
    private final String prizmLocation="c:\\Program Files\\prism-4.4\\bin\\";
    private final String propertyLocationHigh="c:\\Users\\simon5521\\Desktop\\propsHigh.csl";
    private final String propertyLocationLow="c:\\Users\\simon5521\\Desktop\\propsLow.csl";
    private final String propertyLocation="c:\\Users\\simon5521\\Desktop\\props.csl";
    private final String modellLocation="c:\\Users\\simon5521\\Desktop\\modell.pm";
    private final String trueMessage="Result: true";
    private final String falseMessage="Result: false";
    private final String reaultPattern="Result: [0-9]*\\.*[0-9]*";

    private MDPSolver(){

    }

    public static MDPSolver getInstance() {
        return LazyHolder.mdpSolver;
    }

    private static class LazyHolder{
        public static MDPSolver mdpSolver;
    }


    private void writeModellFile(String prizmModell) throws IOException {
        BufferedWriter fileWriter=new BufferedWriter(new FileWriter(modellLocation));
        fileWriter.write(prizmModell);
        fileWriter.close();
    }

    private String startCheckHigh() throws IOException {

        List<String> commandLine = new ArrayList<>(4);
        commandLine.add(prizmmodellcheckerLocation);
        commandLine.add(modellLocation);
        commandLine.add(propertyLocationHigh);

        Process process = new ProcessBuilder()
                .command(commandLine)
                .directory(new File(prizmLocation))
                .start();

        StringBuilder output=new StringBuilder();

        int i = 0;
        while ((i = process.getInputStream().read()) != -1) {
            output.append((char) i);
        }

        return output.toString();
    }

    private String startCheckLow() throws IOException {

        List<String> commandLine = new ArrayList<>(4);
        commandLine.add(prizmmodellcheckerLocation);
        commandLine.add(modellLocation);
        commandLine.add(propertyLocationLow);

        Process process = new ProcessBuilder()
                .command(commandLine)
                .directory(new File(prizmLocation))
                .start();

        StringBuilder output=new StringBuilder();

        int i = 0;
        while ((i = process.getInputStream().read()) != -1) {
            output.append((char) i);
        }

        return output.toString();
    }

}
