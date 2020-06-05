package datamodel;

import classifier.TreeAugmentedNayveBayes;
import datamodel.categories.Category;

import java.util.List;

/**
 * An interface that exposes the services for a DataModel that saves counts extracted from training data.
 * The counts extracted are the ones necessary to calculate a TreeAugmentedNayveBayesClassifier, but might be usefull
 * to other types of classifiers
 * @param <T>       A generic type
 * @see TrainingModel
 * @see TreeAugmentedNayveBayes
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public interface DataModel<T> {

    /**
     * A method that when implemented should update the countings in the DataModel given an array of generic type with data
     * @param data      An array of generic type
     */
    void updateModel(T[] data);

    /**
     * A method that when implemented should update the countings in the DataModel given a list of arrays of generic type with data
     * @param data      A List of arrays of generic type
     */
    void batchUpdateModel(List<T[]> data);

    /**
     * A method that when implemented should return the number of labels a given node has given the node index to be considered.
     * Example: Node 0 (X0) contains 3 values: 0, 1, 2
     * @param parentNode        The index of the node to be considered
     * @return                  The number of labels that node has
     */
    int getNumLabels(int parentNode);

    /**
     * A method that when implemented should return the number of classes that the DataModel can assume.
     * Example: Class can vary from: 0 - 19 - In that case the method should return 20
     * @return      An int with the number of classes that the data can assume
     */
    int getNumClasses();

    /**
     * A method that the when implemented should return the number of categories that a DataModel has
     * Example: We can have X1, X2 and X3 - In that case the method should return 3
     * @return      An int with the number of categories used in the DataModel
     */
    int getNumCategories();

    /**
     * A method that when implemented should return the LaplaceSmoothingTerm used (by default could be 0.5)
     * @return      A double with the LaplaceSmoothingTerm
     */
    double getLaplaceSmoothingTerm();

    /**
     * A method that when implemented should return a the index of a label of a given category
     * Example: We want to know the index of the label 3 in X2 - In this case it could return 2
     * @param category      An int with the index of the category being considered
     * @param label         A generic type with the label we want to get the index of
     * @return              An int with the index of that label
     */
    int getIndexOfCategoryLabel(int category, T label);

    /**
     * A method that when implemented should return a label for a given index of a class.
     * Example: We want to know the label of classification stored at index 0 - In this case it could return "apple"
     * @param c     An int with the index of the classification
     * @return      A generic type with the classification
     */
    T getClassLabel(int c);

    /**
     * A method that when implemented should return an int with a count for a given N_ijkc
     * @param sonNode       An int with the index of the node we are considering
     * @param parentNode    An int with the index of the father of that node
     * @param k             An int with the configuration of the node
     * @param j             An int with the configuration of the father of the node
     * @param c             An int with the classification
     * @return              An int with the number of occurences of that combination
     */
    int getNijkcCount(int sonNode, int parentNode, int k, int j, int c);

    /**
     * A method that when implemented should return an int with a count for a given N_ikc
     * @param sonNode       An int with the index of the node we are considering
     * @param k             An int with the configuration of the node
     * @param c             An int with the classification
     * @return              An int with the number of occurences of that combination
     */
    int getNikcCount(int sonNode, int k, int c);

    /**
     * A method that when implemented should return an int with a count for a given N_ijc
     * @param sonNode       An int with the index of the node we are considering
     * @param parentNode    An int with the index of the father of that node
     * @param j             An int with the configuration of the father of the node
     * @param c             An int with the classification
     * @return              An int with the number of occurences of that combination
     */
    int getNijcCount(int sonNode, int parentNode, int j, int c);

    /**
     * A method that when implemented should return an int with a count for a given N_c
     * @param c             An int with the classification
     * @return              An int with the number of occurences of that combination
     */
    int getNcCount(int c);

    /**
     * A method that when implemented should return an int with the number of training examples used
     * @return          An int with the number of training examples
     */
    int getNCount();

    /**
     * A method that when implemented, given the index of a category (ex. X1, X2, X3) should return the
     * Category object with all the countings for that specific category
     * @param i         The index of the category
     * @return          A Category object
     */
    Category<T> getCategory(int i);

}
