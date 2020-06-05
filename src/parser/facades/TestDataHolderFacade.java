package parser.facades;

import exceptions.EmptyStringException;
import parser.DataHolder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * A class that implements the DataHolder interface. This class saves the data and labels read using the readData and
 * readLabels function into a String array of labels, the data itself into a List of arrays of String and
 * classes from the data into a List of String. This values can be obtained by invoking getLabels(), getData() and
 * getClassifications().
 * This class is a facade ("a middle man") that makes the interface between receiving data from some source, for
 * example a csv file and calling and saving it in an ordered way.
 * @see parser.DataHolder
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public class TestDataHolderFacade implements DataHolder<String> {

    /**
     * An array of String with the labels extracted from the data: ex: X1, X2, X3
     */
    protected String[] labels;

    /**
     * The training data extracted from the data: ex: 0,1,1
     */
    protected List<String[]> data = new LinkedList<>();

    /**
     * The classificatio data: ex: 4
     */
    protected List<String> classifications = new LinkedList<>();

    /**
     * A method that when called, receives an array of String as parameters, saves the last element in a List of String
     * of classifications and saves the other elements of the array in List of arrays of String. This data can then be
     * access by invoking getLabels(), getData() and getClassifications().
     * @param data      An array of generic type
     */
    @Override
    public void readData(String[] data) {
        if(data.length == 0) throw new EmptyStringException("String was empty");

        if(this.labels == null) {
            this.labels = Arrays.copyOfRange(data, 0, data.length-1);
        } else {
            this.data.add(Arrays.copyOfRange(data, 0, data.length-1));
            this.classifications.add(data[data.length-1]);
        }

    }

    /**
     * A method that when invoked, receives an array of String as parameters, saves everything in the array except the
     * last element in an array of String (labels). This array can be accessed by invoking getLabel().
     * @param data      An array of String with labels for the data
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
     * A method that returns a List of String with the classifications data
     * @return      List of String with the classification data
     */
    public List<String> getClassifications() {
        return this.classifications;
    }

    /**
     * Redefinition of the equals() method. Make suse of the list of labels, the data and the classificaiton data to make
     * the comparison
     * @param o     Another TestDataHolderFacade object
     * @return      A boolean with true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestDataHolderFacade)) return false;

        TestDataHolderFacade that = (TestDataHolderFacade) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(labels, that.labels)) return false;
        if (!data.equals(that.data)) return false;
        return classifications.equals(that.classifications);
    }

    /**
     * Redefinition of the hashCode() method. Make suse of the list of labels, the data and the classificaiton data to make
     * the hashcode
     * @return      An int with the hascode
     */
    @Override
    public int hashCode() {
        int result = Arrays.hashCode(labels);
        result = 31 * result + data.hashCode();
        result = 31 * result + classifications.hashCode();
        return result;
    }
}
