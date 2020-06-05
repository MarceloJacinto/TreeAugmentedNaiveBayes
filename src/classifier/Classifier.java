package classifier;

import java.util.List;

/**
 * An interface that exposes the services that a general classifier should offer.
 * @param <T>       A generic type
 * @see AbstractClassifier
 * @see AbstractBayes
 * @see TreeAugmentedNayveBayes
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public interface Classifier<T> {

    /**
     * A method that when invoked should train a classifier
     */
    void train();

    /**
     * A method that when invoked, should receive an array of generic type of data. From that data
     * it should make a prediction and return a generic type with that prediction
     * @param data      An array of data of generic type. This data should be used to make a prediction
     * @return          A generic type with the result of the prediction
     */
    T predictOneExample(T[] data);

    /**
     * A method that when invoked, should receive a list of arrays of generic type of data. From that data
     * it should make severeal predictions on that list of data. Then it should retur a list of generic type with
     * the predictions
     * @param data      A list of arrays of generic type. This data should be used to make several predictions
     * @return          A list of generic type with the result of the predictions
     */
    List<T> predictMultipleExamples(List<T[]> data);
}
