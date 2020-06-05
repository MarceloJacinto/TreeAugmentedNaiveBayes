package utils;

/**
 * The Utils class implements is an abstract class that implements utilitary functions
 * and that do not have a specific place where they belong. This class cannot be instantiated.
 * In order to call a method from this class, do for example: Utils.log2(1.0)
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public abstract class Utils {


    private Utils() {
        return;
    }

    /**
     * This method return the log2(x) as a double
     * @param x     the parameter to calculate the log2(x)
     * @return      returns a double with the result of the calculation
     * @throws      IllegalArgumentException (if x smaller than 0)
     */
    public static double log2(double x) {
        if(x < 0) throw new IllegalArgumentException("Negative logarithms do not exist");
        return (double) (Math.log(x) / Math.log(2));
    }

    /**
     * This method returns a String that is the result of the concatenation of 4 integer
     * i.e. returns "i,j,k,c" (note that every integer is cast to string before joining them together)
     * @param i     an int
     * @param j     an int
     * @param k     an int
     * @param c     an int
     * @return      a String with the concatenation of all integers cast to strings, separated by commas
     */
    public static String keyGenerator(int i, int j, int k, int c) {
        String key = Integer.toString(i) + "," + Integer.toString(j) + "," + Integer.toString(k) + "," + Integer.toString(c);
        return key;
    }

    /**
     * This method returns a String that is the result of the concatenation of 4 integer
     * i.e. returns "i,j,k,c" (note that every integer is cast to string before joining them together)
     * @param i     an int
     * @param k     an int
     * @param c     an int
     * @return      a String with the concatenation of all integers cast to strings, separated by commas
     */
    public static String keyGenerator(int i, int k, int c) {
        String key = Integer.toString(i) + "," + Integer.toString(k) + "," + Integer.toString(c);
        return key;
    }

    /**
     * This method finds the index of the maximum value on an array of doubles
     * @param data  an array of doubles that we wish to find the maximum value's index
     * @return      an int with the maximum value's index
     */
    public static int findMaxIndex(double[] data) {
        int maxInd = 0;
        double max = data[0];
        for(int i = 0; i < data.length; i++) {
            /*System.out.print("\n data[i]: " + data[i] + ", min_value: " + max + "\n");*/
            if(data[i] > max) {
                max = data[i];
                maxInd = i;
                /*System.out.println("correu");*/
            }
        }
        return maxInd;
    }

    /**
     * This method checks the arguments of the main program and if they respect the specifications
     * @param args  an array of String that should contain "train test score". The score must be "LL" or "MDL".
     *              If the specification is not respected, the program exits with a message on how to use the program
     */
    public static void checkArguments(String[] args) {
        if(args.length != 3 && args.length != 4) {
            System.out.println("Usage: TAN </train_file.csv> </test_file.csv> <score>");
            System.out.println("The available scores are: LL and MDL");
            System.out.println("You can give an extra argument: c - if you want to see the confusion matrix");
            System.exit(1);
        }

        if((!args[2].equals("LL")) && (!args[2].equals("MDL"))) {
            System.out.println("The available scores are: LL and MDL");
            System.exit(1);
        }

        if(args.length == 4) {
            if(!args[3].equals("c") && !args[3].equals("C")) {
                System.out.println("The extra option is: c - for confusion matrix");
                System.exit(1);
            }
        }
    }
}
