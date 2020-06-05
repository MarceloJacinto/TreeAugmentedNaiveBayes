package metrics.perclass;

/**
 * This class extends AbstractAuxiliaryScores and implements the method doCalculateScore using the formula
 * to calculate the number of Specificity on multi-class problems
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
public class SpecificityPerClass extends AbstractAuxiliaryScores {

    /**
     * The true negative object
     */
    private SingleScore tn;

    /**
     * The false positive object
     */
    private SingleScore fp;

    /**
     * Constructor for the SpecificityPerClass, receives a matrix of int (int[][] - confusion matrix) used to calculate the
     * scores of individual classes
     * @param confusionMatrix       A matrix of int - int[][] that represents a confusion matrix (with the predictions in the lines
     *                              and real classes in the columns)
     */
    public SpecificityPerClass(int[][] confusionMatrix) {
        super(confusionMatrix);
        tn = new TNPerClass(confusionMatrix);
        fp = new FPPerClass(confusionMatrix);
    }

    /**
     * Implementation of the doCalculateScore method. When an index is received, calculates the score of
     * that particular class using the Specificity formula for multi-class problems
     * @param i         An int with the index (in the matrix) to calculate the score
     * @return          A double with the score
     */
    @Override
    protected double doCalculateScore(int i) {

        double tnvalue = tn.getScore(i);
        double fpvalue = fp.getScore(i);

        /* Dealing with indeterminations in the formula */
        if(fpvalue == 0 && tnvalue == 0) return 0;
        if(tnvalue == 0 ) return 0;

        return tnvalue / (tnvalue + fpvalue);
    }
}
