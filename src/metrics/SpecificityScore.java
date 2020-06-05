package metrics;

import metrics.perclass.SingleScore;
import metrics.perclass.SpecificityPerClass;

import java.util.List;

/**
 * This is a class that extends the AbstractMultipleCategoryScore. It implements the calculation of a score
 * using the Specificity score formula. This class receives a ConfusionMatrix object that must be set (in it's constructor).
 * In alternative one can pass a list of predictions and a list of real classifications of generic type (as long as the type
 * implements the Comparable interface)
 * @see MultipleScore
 * @see SingleScore
 * @see SpecificityPerClass
 * @see ConcreteConfusionMatrix
 * @see ConfusionMatrix
 * @param <T>   a generic type for the class that extends Comparable
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public class SpecificityScore<T extends Comparable<T>> extends AbstractMultipleCategoryScore<T> {

    /**
     * Constructor for the Specificity. After receiving a ConfusionMatrix object it calculates the score for each class
     * @see SpecificityPerClass
     * @see ConcreteConfusionMatrix
     * @see ConfusionMatrix
     * @param confusionMatrix       A ConfusionMatrix object that must be set
     */
    public SpecificityScore(ConfusionMatrix<T> confusionMatrix) {
        super(confusionMatrix, new SpecificityPerClass(confusionMatrix.getConfusionMatrix()));
    }

    /**
     * A method to return the list of Doubles with the calculated scores for each class of the ConfusionMatrix as well
     * as a weighted average of all results in the last element of the list
     * @return      A List of Double with the scores for each class + a weighted average of all scores in the last element
     */
    @Override
    protected List<Double> doGetScores() {
        return this.score;
    }

    /**
     * A method that returns a List of generic type that extend Comparables. This list contains the labels for each score
     * and contains 1 less element than the list of scores ( as the last score is a weighted average of the other scores).
     * @return      A List of generic type that extends comparable with the labels of each class
     */
    @Override
    protected List<T> doGetLabels() {
        return this.confusionMatrix.getLabels();
    }

    /**
     * A method that returns a String with the name of the score used to calculate the scores
     * @return      A String with the name of the score formula used
     */
    @Override
    protected String doGetScoreName() {
        return "Specificity";
    }
}
