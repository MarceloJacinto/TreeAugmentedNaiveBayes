package metrics.perclass;

/**
 * This class extends AbstractAuxiliaryScores and implements the method doCalculateScore using the formula
 * to calculate the number of Sensitivity on multi-class problems
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
public class SensitivityPerClass extends AbstractAuxiliaryScores {

    /**
     * The true positive object
     */
    private SingleScore tp;

    /**
     * The false negative object
     */
    private SingleScore fn;

    /**
     * Constructor for the SensitivityPerClass, receives a matrix of int (int[][] - confusion matrix) used to calculate the
     * scores of individual classes
     * @param confusionMatrix       A matrix of int - int[][] that represents a confusion matrix (with the predictions in the lines
     *                              and real classes in the columns)
     */
    public SensitivityPerClass(int[][] confusionMatrix) {
        super(confusionMatrix);
        tp = new TPPerClass(confusionMatrix);
        fn = new FNPerClass(confusionMatrix);
    }

    /**
     * Implementation of the doCalculateScore method. When an index is received, calculates the score of
     * that particular class using the Sensitivity formula for multi-class problems
     * @param i         An int with the index (in the matrix) to calculate the score
     * @return          A double with the score
     */
    @Override
    protected double doCalculateScore(int i) {

        double tpvalue = tp.getScore(i);
        double fnvalue = fn.getScore(i);

        /* Dealing with undertermination in the formula */
        if(fnvalue == 0 && tpvalue == 0) return 0;
        if(tpvalue == 0) return 0;

        return tpvalue / (tpvalue + fnvalue);
    }
}
