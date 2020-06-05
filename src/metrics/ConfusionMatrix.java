package metrics;

import java.util.List;

/**
 * An interface that settles the foundation for the creation of a confusion matrix given a set of that of generic type
 * that extends Comparable. This interface provides the functionality of getting a confusion matrix, the corresponding labels
 * and the percentage of each class in the real data. The Generic type for the labels must extend the Comparable interface
 * in order to make possible the ordering of labels in the confusion matrix (with the goal of producing "clean data").
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public interface ConfusionMatrix<T extends Comparable<T>> {
    /**
     * A method that returns a matrix of int[][] with the actual confusion matrix
     * @return      A matrix of int with the real confusion matrix
     */
    int[][] getConfusionMatrix();

    /**
     * A method that returns a list of labels ordered according to the lines/columns of the confusion matrix
     * @return      A List of generic type that are the labels for each line/column
     */
    List<T> getLabels();

    /**
     * A method that returns an array with the percentage of each real class in the data
     * @return      An array of double with percentages normalized by 100
     */
    double[] getPercentageOfEachClass();

    /**
     * A method that given the index in the confusion matrix, returns the percentage of data with the real class
     * @param i     The index in the confusion matrix
     * @return      A double with the percentage normalized by 100
     */
    double getPercentageOfIndividualClass(int i);
}
