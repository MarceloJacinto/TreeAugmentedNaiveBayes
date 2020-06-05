package metrics.perclass;

/**
 * This class extends AbstractAuxiliaryScores and implements the method doCalculateScore using the formula
 * to calculate the number of True Positives on multi-class problems
 * @see AbstractAuxiliaryScores
 * @see FNPerClass
 * @see FPPerClass
 * @see TNPerClass
 * @see TPPerClass
 * @see SensitivityPerClass
 * @see SpecificityPerClass
 * @see F1PerClass
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public class TPPerClass extends AbstractAuxiliaryScores {

    /**
     * Constructor for the TPPerClass, receives a matrix of int (int[][] - confusion matrix) used to calculate the
     * scores of individual classes
     * @param confusionMatrix       A matrix of int - int[][] that represents a confusion matrix (with the predictions in the lines
     *                              and real classes in the columns)
     */
    public TPPerClass(int[][] confusionMatrix) {
        super(confusionMatrix);
    }

    /**
     * Implementation of the doCalculateScore method. When an index is received, calculates the score of
     * that particular class using the True Positives formula for multi-class problems
     * @param i         An int with the index (in the matrix) to calculate the score
     * @return          A double with the score
     */
    @Override
    protected double doCalculateScore(int i) {
        int[][] confusionMatrix = super.getConfusionMatrix();

        if(i >= confusionMatrix.length)
            throw new IndexOutOfBoundsException("Confusion matrix is only has " + confusionMatrix.length + " classes");

        return confusionMatrix[i][i];
    }
}