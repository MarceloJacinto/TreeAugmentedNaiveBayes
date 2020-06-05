package datamodel;

import classifier.TreeAugmentedNayveBayes;

import java.util.List;

/**
 * An interface that exposes the services for a model that saves labels of a generic type
 * @param <T>       A generic type
 * @see datamodel.classifications.ModelClassfication
 * @see datamodel.classifications.Classification
 * @see datamodel.categories.Category
 * @see datamodel.categories.ModelCategory
 * @see TreeAugmentedNayveBayes
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public interface Model<T> {

    /**
     * A method that when implemented shoudl get the number of labels saved inside a Model
     * @return      An int with the total number of labels saved
     */
    int getNumLabels();

    /**
     * A method that when implemented should return a List of generic (with the labels) saved
     * @return      A List of generic type
     */
    List<T> getAllLabels();

    /**
     * A method that when implemented should save a label in the Model if it does not exist there yet.
     * Should return true if the label was added and return false if not
     * @param data      A generic type with a label to be saved
     * @return          A boolean that is true if the label was saved
     */
    boolean addLabelIfNotExistent(T data);

    /**
     * A method that when implemented should return the index of a label in the Model if it exists
     * @param data      A generic type with a label
     * @return          An int with the index of that label in the Model
     */
    int getLabelIndex(T data);

    /**
     * A method that when implemented, given an index should return the label saved
     * @param i         An int with the index of a given label
     * @return          A generic type with the corresponding label
     */
    T getLabelOfIndex(int i);
}
