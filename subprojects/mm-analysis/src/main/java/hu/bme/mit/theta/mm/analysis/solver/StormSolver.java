package hu.bme.mit.theta.mm.analysis.solver;

import hu.bme.mit.theta.mm.generator.MMPRISMWriter;
import hu.bme.mit.theta.mm.model.MarkovianModel;
import hu.bme.mit.theta.mm.prop.Property;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StormSolver extends ExternalSolver{




        private final String modelChecker="storm";

        private final String modelCheckerLocation="/home/simon5521/Programs/prism/prism-4.4-linux64/bin";
        private final String tempPropertyLocation="/home/simon5521/Desktop/mm_solve_autogen.pctl";
        private final String tempModelLocation="/home/simon5521/Desktop/mm_solve_autogen.nm";

    public StormSolver() {
        super("Result \\(for initial states\\): [0-9]*\\.*[0-9]*", "Result \\(for initial states\\): [a-z]*");
    }

    @Override
        protected List<String> generateCommand() {
            List<String> commandLine = new ArrayList<>(4);
            commandLine.add(modelChecker);
            commandLine.add("--prism");
            commandLine.add(tempModelLocation);
            commandLine.add("--prop");
            commandLine.add(tempPropertyLocation);
            return commandLine;
        }

        @Override
        protected Scanner runSolver(List<String> commandLine) throws IOException {

            Process process = new ProcessBuilder()
                    .command(commandLine)
                    .directory(new File(modelCheckerLocation))
                    .start();

            /*
            Scanner inputStreamReader=new Scanner(process.getInputStream());
            while (inputStreamReader.hasNextLine()) System.out.println(inputStreamReader.nextLine());
            */


            return new Scanner(new BufferedInputStream(process.getInputStream()));

        }

        @Override
        protected void writeModellFile(String prizmModell) throws IOException {

            BufferedWriter fileWriter=new BufferedWriter(new FileWriter(tempModelLocation));
            fileWriter.write(prizmModell);
            fileWriter.close();

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


}
