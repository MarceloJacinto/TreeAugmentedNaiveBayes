package metrics.perclass;

/**
 * This class extends AbstractAuxiliaryScores and implements the method doCalculateScore using the F1Score formula.
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
public class F1PerClass extends AbstractAuxiliaryScores {

    /**
     * The true positive object
     */
    private SingleScore tp;

    /**
     * The false negative object
     */
    private SingleScore fn;

    /**
     * The false positive object
     */
    private SingleScore fp;

    /**
     * Constructor for the F1PerClass, receives a matrix of int (int[][] - confusion matrix) used to calculate the
     * scores of individual classes
     * @param confusionMatrix       A matrix of int - int[][] that represents a confusion matrix (with the predictions in the lines
     *                              and real classes in the columns)
     */
    public F1PerClass(int[][] confusionMatrix) {
        super(confusionMatrix);
        tp = new TPPerClass(confusionMatrix);
        fn = new FNPerClass(confusionMatrix);
        fp = new FPPerClass(confusionMatrix);
    }

    /**
     * Implementation of the doCalculateScore method. When an index is received, calculates the score of
     * that particular class using the F1Score formula.
     * @param i         An int with the index (in the matrix) to calculate the score
     * @return          A double with the score
     */
    @Override
    protected double doCalculateScore(int i) {
        double tpvalue = tp.getScore(i);
        double fnvalue = fn.getScore(i);
        double fpvalue = fp.getScore(i);

        if ((2 * tpvalue)+fpvalue+fnvalue == 0) return 0;
        if (tpvalue == 0) return 0;

        return (2 * tpvalue) / ((2 * tpvalue)+fpvalue+fnvalue);
    }
}
