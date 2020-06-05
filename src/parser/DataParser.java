package parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * An abstract class that implements the static method readFile. This class cannot be instantiated an in order to
 * invoke it's method one should run: DataParser.readFile(fileName, splitSymbol, dataholder);
 * @see DataHolder
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public abstract class DataParser {

    /**
     * The constructor of this class is private to prevent it from being instantiated
     */
    private DataParser() {
       return;
    }

    /**
     * A static method responsible to opening the csv file specified by the fileName parameter. The csv file should
     * be separated by the split symbol specified by the splitSymbol parameter. This method must also receive as a
     * parameter a DataHolder object that must be previously instantiated. This object is needed as the CSV parser does
     * not know how the data being read must be saved. Therefore, when the first line is read from the file, the
     * method readLabels implemented by the dataholder is called. For the rest of the file the method readData() is
     * called.
     * @param fileName      a String with the /path/name of the csv file to read from
     * @param splitSymbol   a String with the split symbol to be used
     * @param dataholder    a DataHolder object in order to process the data that is being read
     */
    public static void readFile(String fileName, String splitSymbol, DataHolder dataholder) {
        boolean firstLine = true;

        /*Check if the DataHolder object is instantiated */
        if(dataholder == null) throw new NullPointerException("DataHolder object was not instantiated");

        try{
            File dataFile = new File(fileName);
            Scanner dataScanner = new Scanner(dataFile);
            while(dataScanner.hasNextLine()){
                String[] data = dataScanner.nextLine().split(splitSymbol);
                if((data.length == 1 && data[0].equals("")) || data.length == 0) {
                    //continue and ignore empty line
                } else {
                    if(firstLine){
                        dataholder.readLabels(data);
                        firstLine = false;
                    } else {
                        dataholder.readData(data);
                    }
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println("Could not open the file: " + fileName + ". \n I see you try to crash me! Not this time");
            System.exit(1);
        }
    }
}
