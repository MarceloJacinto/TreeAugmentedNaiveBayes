package parser.facades;

import exceptions.EmptyStringException;
import parser.DataHolder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * A class that implements the DataHolder interface. This class saves the data and labels read using the readData and
 * readLabels function into a String array of labels, the data itself into a List of arrays of String.
 * This values can be obtained by invoking getLabels() and getData(). Unlike the TrainingDataHolderFacade, this class
 * does not separate the last column of data read from the rest of the data.
 * This class is a facade ("a middle man") that makes the interface between receiving data from some source, for
 * example a csv file and calling and saving it in an ordered way.
 * @see parser.DataHolder
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public class TrainingDataHolderFacade implements DataHolder<String> {

    /**
     * An array with the labels read from the data: ex: X1, X2, X3
     */
    protected String[] labels;

    /**
     * An list of array of string with read data: ex: 1,1,1,4
     */
    protected List<String[]> data = new LinkedList<>();

    /**
     * A method that when called, receives an array of String as parameters, saves the elements of the array in List of
     * arrays of String. This data can then be accessed by invoking getData().
     * @param data      An array of String with data
     */
    @Override
    public void readData(String[] data) {
        if(data.length == 0) throw new EmptyStringException("String was empty");

        if(this.labels == null) {
            this.labels = Arrays.copyOfRange(data, 0, data.length-1);
        } else {
            this.data.add(data);
        }
    }

    /**
     * A method that when invoked,, receives an array of String. This data can be latter accessed by invoking
     * getLabels()
     * @param data      An array of String with labels
     */
    @Override
    public void readLabels(String[] data) {
        if(data.length == 0) throw new EmptyStringException("String was empty");

        /* we must make a copy in order to ignore the "class" label */
        this.labels = Arrays.copyOfRange(data, 0, data.length-1);
    }

    /**
     * A method that returns an array of String with the labels read previously.
     * @return      Array of String with labels
     */
    public String[] getLabels(){
        return this.labels;
    }

    /**
     * A method that returns a List of arrays of String with the data read previously
     * @return      List of Arrays of String with data
     */
    public List<String[]> getData() {
        return this.data;
    }

    /**
     * Redefinition of the equals() method. Makes use of the labels and data saved to make the comparison
     * @param o     Another TrainingDataHolderFacade object
     * @return      A boolean with true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainingDataHolderFacade)) return false;

        TrainingDataHolderFacade that = (TrainingDataHolderFacade) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(labels, that.labels)) return false;
        return data.equals(that.data);
    }

    /**
     * Redefinition of the hashCode() method. Makes use of the labels and data saved to make the hashcode
     * @return      An int with the hashcode
     */
    @Override
    public int hashCode() {
        int result = Arrays.hashCode(labels);
        result = 31 * result + data.hashCode();
        return result;
    }
}
