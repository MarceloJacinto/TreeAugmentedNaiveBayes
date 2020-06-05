package datamodel.classifications;

import classifier.TreeAugmentedNayveBayes;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * A class that implements the Classification interface. This class can be usefull to store classification data
 * and the number of times it appears in training data.
 * @param <T>       A generic type
 * @see datamodel.Model
 * @see datamodel.classifications.Classification
 * @see datamodel.categories.Category
 * @see datamodel.categories.ModelCategory
 * @see TreeAugmentedNayveBayes
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-06
 */
public class ModelClassfication<T> implements Classification<T> {

    /**
     * The total number of different classes observed during training
     */
    protected int numClasses;

    /**
     * The list of labels of each class observed: Example - orange, blue, green, ...
     */
    protected List<T> labels;

    /**
     * An HashMap with counts for the N_c indexed by the position of a given label in the labels list
     */
    protected HashMap<Integer, Integer> N_c;

    /**
     * The constructor for the ModelClassification. Instantiates the list of labels and the hashmap that saves the Nc counts
     */
    public ModelClassfication() {
        numClasses = 0;
        labels = new LinkedList<T>();
        N_c = new HashMap<Integer, Integer>();
    }

    /**
     * A method that returns the total number of classifications saved until this point
     * @return      An int with the number of classifications
     */
    @Override
    public int getNumLabels() {
        return numClasses;
    }

    /**
     * A method that returns all the classifications saved until this point
     * @return      A List of generic type with all the possible classifications
     */
    @Override
    public List<T> getAllLabels() {
        return labels;
    }

    /**
     * A method that adds a new classification if it does not exist yet in the list
     * @param data      A generic type with a label to be saved
     * @return          A boolean that returns true if the label was added to the list
     */
    @Override
    public boolean addLabelIfNotExistent(T data) {
        if(!labels.contains(data)) {
            labels.add(data);
            numClasses++;
            return true;
        }
        return false;
    }

    /**
     * A method that increments the number of counts for a given classification
     * @param label         A generic type with the classification
     */
    @Override
    public void incrementNcCount(T label) {
        int labelIndex = labels.indexOf(label);
        if(labelIndex != -1) incrementNcCount(labelIndex);
    }

    /**
     * A method that increments the number of counts for a given classification given its index
     * in the list of classifications stored
     * @param labelIndex    An int with the index of the classification we want to increment the count
     */
    @Override
    public void incrementNcCount(int labelIndex) {
        if(N_c.containsKey(labelIndex)) {
            int count = N_c.get(labelIndex);
            count += 1;
            N_c.replace(labelIndex, count);
        } else {
            N_c.put(labelIndex, 1);
        }
    }

    /**
     * A method that returns the number of counts for a given classification (given the index of that classification)
     * @param c         An int with the index of the classification
     * @return          An int with the counts for that classification
     */
    @Override
    public int getNcCount(int c) {
        if(N_c.containsKey(c)) return N_c.get(c);
        return 0;
    }

    /**
     * A method that returns the number of counts for a given classification (given the classification itself)
     * @param label     A generic type with the classification
     * @return          An int with the counts for that classification
     */
    @Override
    public int getNcCount(T label) {
        int c = labels.indexOf(label);
        if(c == -1) {
            return 0;
        }
        return getNcCount(c);
    }

    /**
     * A method that given a classification should return the number of times it appeared
     * @param data      A generic type with a classification
     * @return          An int with the number of times it appeared
     */
    @Override
    public int getLabelIndex(T data) {
        return labels.indexOf(data);
    }

    /**
     * A method that given the index of a classification should return the number of times it appeared
     * @param i         An int with the index of a given classification
     * @return          An int with the number of times it appeared
     */
    @Override
    public T getLabelOfIndex(int i) {
        if(i >= labels.size()) throw new ArrayIndexOutOfBoundsException("There are only " + labels.size() + "labels");
        return labels.get(i);
    }

    /**
     * Redefinition of the toString() method, printing the information on the classes, the labels and the counts of different
     * classes
     * @return       A String with the relevant information
     */
    @Override
    public String toString() {
        return "ModelClassfication{" +
                "numClasses=" + numClasses +
                ", labels=" + labels +
                ", N_c=" + N_c +
                '}';
    }

    /**
     * Redefinition of the equals() method. Uses the number of classes, its labels and counts to make the comparison
     * @param o     Another ModelClassfication
     * @return      A boolean with true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModelClassfication)) return false;

        ModelClassfication<?> that = (ModelClassfication<?>) o;

        if (numClasses != that.numClasses) return false;
        if (!labels.equals(that.labels)) return false;
        return N_c.equals(that.N_c);
    }

    /**
     * Redefinition of the hashcode method. Uses the number of classes, its labels and counts to make the comparison
     * @return      An int with the hashcode
     */
    @Override
    public int hashCode() {
        int result = numClasses;
        result = 31 * result + labels.hashCode();
        result = 31 * result + N_c.hashCode();
        return result;
    }
}
