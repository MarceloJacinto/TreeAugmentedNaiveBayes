package metrics;

import java.util.List;

/**
 * An interface that provides 2 methods: getScore() and getLabels(). The first method should return a List of
 * Doubles with scores for multi-class classifications (hence the name MultipleScore) as well as a weighted average
 * of those score. The second method should provide a List of generic types with the categories/labels that the data
 * could take (with the same order as the list of doubles).
 * @param <T>   a generic type for the class
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public interface MultipleScore<T> {

    /**
     * A method that when implemented should return a List of Doubles with the scores for a multi-class classification
     * as well as a weighted average of those scores
     * @return      A List of Double with the scores
     */
    List<Double> getScore();

    /**
     * A method that when implemented should return a List of generic types with the labels of each individual
     * score class
     * @return      A List of Labels of the class of each score of generic type
     */
    List<T> getLabels();
}
