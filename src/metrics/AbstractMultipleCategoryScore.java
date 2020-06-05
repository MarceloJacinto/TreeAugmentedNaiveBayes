package metrics;

import metrics.perclass.SingleScore;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This is an abstract class that implements the MultipleScore interface. It also implements an auxiliary function that can be used
 * (doCalculateScore) that given a ConfusionMatrix and a SingleScore calculates a score for multiple class problems. This
 * method can be used (even though is not enforce) by classes that extends this class
 * @see MultipleScore
 * @see SingleScore
 * @param <T>   a generic type for the class that extends Comparable
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public abstract class AbstractMultipleCategoryScore<T extends Comparable<T>> implements MultipleScore<T> {

    /**
     * A list of scores with a weighted average in the end
     */
    protected List<Double> score;

    /**
     * A confusion matrix to make the calculations with
     * @see ConfusionMatrix
     * @see ConcreteConfusionMatrix
     */
    protected ConfusionMatrix<T> confusionMatrix;

    /**
     * The score to be used to calculate the scores for the classes
     * @see metrics.perclass.SingleScore
     * @see metrics.perclass.F1PerClass
     * @see metrics.perclass.SpecificityPerClass
     * @see metrics.perclass.SensitivityPerClass
     */
    protected SingleScore individualScore;

    /**
     * Constructor for the AbstractMultipleCategoryScore that receives a confusionMatrix and a score
     * to be used to calculate the metric for each class and the weighted average
     * @param confusionMatrix       A Confusion Matrix
     * @param individualScore       The score to be used
     * @see ConcreteConfusionMatrix
     * @see ConfusionMatrix
     * @see metrics.perclass.SingleScore
     * @see metrics.perclass.F1PerClass
     * @see metrics.perclass.SpecificityPerClass
     * @see metrics.perclass.SensitivityPerClass
     */
    public AbstractMultipleCategoryScore(ConfusionMatrix confusionMatrix, SingleScore individualScore) {
        if(confusionMatrix == null)
            throw new NullPointerException("ConfusionMatrix points to null");
        this.confusionMatrix = confusionMatrix;
        this.individualScore = individualScore;
        this.score = doCalculateScore();
    }

    /**
     * An abstract method to be implemented a posteriori that returns a list of scores for each class and a weighted
     * average in the last element of the list
     * @return      List of Double with the score for each class and the weighted average of those scores in the last element
     */
    protected abstract List<Double> doGetScores();

    /**
     * An abstract method to be implemented a posteriori that return the labels corresponding to the classes that were
     * used to calculate the scores. This list should contain one less element than the List of scores, as the last score
     * is a weighted average of all scores
     * @return      List of Generic type that extends comparable
     */
    protected abstract List<T> doGetLabels();

    /**
     * An abstract method to be implemented a posteriori that returns a String with the name of the score that was used for
     * the calculationg (usefull in the toString(), for example).
     * @return      A String with the name of the score that was used to calculate the List of scores
     */
    protected abstract String doGetScoreName();

    /**
     * A method that calculates the score for a given number of classes in a confusion matrix received in the parameters.
     * The score is calculated for every class of the matrix using the method getScore() prodivded in the single score
     * class.
     * @see ConcreteConfusionMatrix
     * @see SingleScore
     * @return                      A List of Double with the score for every class and the weighted average of those scores
     *                              in the last element of the list
     */
    protected List<Double> doCalculateScore() {

        if (this.confusionMatrix == null || this.individualScore == null)
            throw new NullPointerException("Null argument");

        int[][] matrix = this.confusionMatrix.getConfusionMatrix();
        List<Double> individualScores = new ArrayList<Double>(this.confusionMatrix.getLabels().size()+1);

        double weightedAvg = 0;
        for(int i = 0; i < matrix.length; i++) {
            double localScore = this.individualScore.getScore(i);
            individualScores.add(i, localScore);
            weightedAvg += (localScore * this.confusionMatrix.getPercentageOfIndividualClass(i));
        }
        individualScores.add(weightedAvg);

        return individualScores;
    }

    /**
     * A method that returns a List of Doubles with the scores for the multi-class classification. Internally call
     * the doGetScore abstract method that must be implemented a-posteriori
     * @return      returns a list of Doubles with the score
     */
    @Override
    public List<Double> getScore() {
        return doGetScores();
    }

    /**
     * A method that return s List of generics (with labels) for the correspoding scores. This list should contain one less
     * element that the list returned by getScore, as getScore returns in the last element a weighted average of the all the
     * scores
     * @return      A list of generic type with the labels
     */
    @Override
    public List<T> getLabels() {
        return doGetLabels();
    }

    /**
     * Redefinition of the toString() method that returns Scorename:[label1:score, label2:score, ..., avg]
     * @return      returns a String with Scorename:[label1:score, label2:score, ..., avg]
     */
    @Override
    public String toString() {
        Iterator<T> labelIt = doGetLabels().iterator();
        Iterator<Double> scoreIt = doGetScores().iterator();
        String s = doGetScoreName() + "[";
        while(labelIt.hasNext()) {
            String label = labelIt.next().toString();
            Double auxScore = scoreIt.next();
            s += label + ":" + String.format("%.2f",auxScore) +", ";
        }
        Double auxScore = scoreIt.next();
        s += String.format("%.2f",auxScore) + "]";
        return s;
    }

    /**
     * Redefinition of the equals() method. Makes use of the score itself and the confusionMatrix object used
     * @param o     Another AbstractMultipleCategoryScore object
     * @return      A boolean with true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractMultipleCategoryScore)) return false;

        AbstractMultipleCategoryScore<?> that = (AbstractMultipleCategoryScore<?>) o;

        if (!score.equals(that.score)) return false;
        return confusionMatrix.equals(that.confusionMatrix);
    }

    /**
     * Redefinition of the hashCode() method. Makes use of the score itself and the confusionMatrix object used
     * @return An int with the hashcode
     */
    @Override
    public int hashCode() {
        int result = score.hashCode();
        result = 31 * result + confusionMatrix.hashCode();
        return result;
    }
}
