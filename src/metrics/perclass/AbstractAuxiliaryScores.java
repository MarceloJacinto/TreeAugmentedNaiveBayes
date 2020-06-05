package metrics.perclass;

/**
 * This is an abstract class implements the SingleScore interface and it is a blueprint for the calculation of
 * scores for invidual classes given a confusion matrix( a matrix of int - int[][]).
 * @see SingleScore
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public abstract class AbstractAuxiliaryScores implements SingleScore {

    /**
     * A confusion matrix to calculate the data from
     */
    private int[][] confusionMatrix;

    /**
     * Constructor for the AbstractAuxiliaryScores class. It receives a confusion matrix as argument (int[][])
     * @param confusionMatrix       A matrix of int (int[][]) that represents a confusion matrix with the lines representing
     *                              the predictions and the column representing the real classifications
     */
    public AbstractAuxiliaryScores(int[][] confusionMatrix) {
        this.confusionMatrix = confusionMatrix;
    }

    /**
     * An abstract method to be implemented a posteriori that receives the index of the class in the matrix to calculate
     * the score from and returns a double with a score for that particular class
     * @param i         An int with the index (in the matrix) to calculate the score
     * @return          A double with the score for the individual class
     */
    protected abstract double doCalculateScore(int i);

    /**
     * A method to allow the user to get the calculated score for a particular class. This method invokes the doCalculateScore
     * method inside.
     * @param i         An int with the index (in the matrix) to calculate the score
     * @return          A double with the score for the individual class
     */
    @Override
    public double getScore(int i) {
        return doCalculateScore(i);
    }

    /**
     * A method that returns a matrix of int (int[][]) with the confusion matrix used to calculate the scores
     * @return      A matrix of int (int[][]) - the confusion matrix used to calculate the scores
     */
    protected int[][] getConfusionMatrix() {
        return confusionMatrix;
    }

}
