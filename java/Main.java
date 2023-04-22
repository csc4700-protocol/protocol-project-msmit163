// DO NOT CHANGE THIS FILE

import java.io.*;


class Main {

    public static void main(String[] args) {
        String[] inputValues = processArgs(args);

        System.out.print("Processing input: ");
        for (String s : inputValues) {
            System.out.print(s + " ");
        }
        System.out.println();

        Parser parser = new Parser();

        try {
            parser.parse(inputValues);
        }
        catch (Exception e) {
            System.out.println("Error parsing protocol: " + e.getClass().getCanonicalName());
        }
    }

    private static String[] processArgs(String[] args) {
        if (args.length == 0) {
            System.out.println("Please specify one of the following:");
            System.out.println("  * Space separated list of input values");
            System.out.println("  * The argument -f followed by the full path to an input file");
            System.exit(1);
        }
        else if (args[0].equals("-f")) {
            System.out.println("Reading file: " + args[1]);
            File readMe = new File (args[1]);

            try {
                BufferedReader br = new BufferedReader(new FileReader(readMe));
                String line = br.readLine();
                br.close();

                String[] inputValues = line.split("\\s+");
                return inputValues;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            return args;
        }
        return null;
    }

}