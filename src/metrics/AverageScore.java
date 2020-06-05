package metrics;

/**
 * An interface that provides 1 method: getScore(). The method should return a Double with score for a multi-class
 * classification problem. Examples os such scores would be accuracy scores, a weighted average of scores, etc
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public interface AverageScore {

    /**
     * A method that when implemented should return a Double with the score for a multi-class classification
     * such as an accuracy score, a weighted score considering multi-class problems, etc
     * @return      A List of Double with the scores
     */
    double getScore();
}
