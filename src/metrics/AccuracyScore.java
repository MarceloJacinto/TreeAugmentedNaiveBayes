package metrics;

import java.util.Iterator;
import java.util.List;

/**
 * This class implements the AverageScore interface. This class implements an accuracy score calculation given a list of predicted data and actual classification data.
 * In order to get the score one should invoke getScore() method.
 * @param <T>   a generic type for the class
 * @see AverageScore
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public class AccuracyScore<T> implements AverageScore{

    /**
     * The actual score
     */
    private double score;

    /**
     * The constructor method. Receives a list of generic type with the predicted data and the actual classification data.
     * Then with that data, calculates a score and saves it inside a double. In order to access that score, one should
     * invoke getScore() method.
     * @param predicted     A List of generics with the predicted data
     * @param actual        A List of generics with the actual classification data
     */
    public AccuracyScore(List<T> predicted, List<T> actual) {
        /* Protect this metric because predicted and actual should have the same size*/
        if(predicted.size() != actual.size())
            throw new IllegalArgumentException("The list sizes do not match");

        score = doCalculateScore(predicted, actual);
    }

    /**
     * A private auixiliary method used to calculate the actual accuracy score, given a list of predicted data and a list
     * of actual classification data.
     * @param predicted     A List of generics with the predicted data
     * @param actual        A List of generics with the actual classification data
     * @return              A double with the accuracy score
     */
    private double doCalculateScore(List<T> predicted, List<T> actual) {
        /* Protect this metric because predicted and actual should have the same size*/
        if(predicted.size() != actual.size())
            throw new IllegalArgumentException("The list sizes do not match");

        Iterator pdIt = predicted.iterator();
        Iterator acIt = actual.iterator();

        double count = 0;

        while(pdIt.hasNext()) {
            T pdLabel = (T) pdIt.next();
            T acLabel = (T) acIt.next();
            count += (pdLabel.equals(acLabel)) ? 1 : 0;
        }
        return count / predicted.size();
    }

    /**
     * A method that returns the accuracy score previously calculated.
     * @return              A double with the accuracy score
     */
    @Override
    public double getScore() {
        return this.score;
    }

    /**
     * Redefintion of the equals() method. Uses the score itself to make the comparison
     * @param o     Another AccuracyScore object
     * @return      A boolean with true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccuracyScore)) return false;

        AccuracyScore<?> that = (AccuracyScore<?>) o;

        return Double.compare(that.score, score) == 0;
    }

    /**
     * Redefintion of the hashCode() method. Uses the score itself to make the comparison
     * @return      An int with the hashcode
     */
    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(score);
        return (int) (temp ^ (temp >>> 32));
    }
}
