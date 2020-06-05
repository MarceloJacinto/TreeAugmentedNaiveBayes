package score;

import datamodel.DataModel;

/**
 * A class that extends the LLScore class. It implements the method calculateScore by calculating the
 * minimum description length score for a node and it's parent given their indexes and a DataModel with countings from
 * training data. This class extends LLScore and not AbstractScore as the formula for the minimum description length
 * makes use of the formula defined by the Loglikelihood score.
 * @see Score
 * @see AbstractScore
 * @see LLScore
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public class MDLScore extends LLScore {

    /**
     * A method that calculates a score given a node and it's parent index as well as a DataModel with countings
     * obtained from training data. The score is calculated according to the MDL formula.
     * @param i                 an int to indicate the node itself
     * @param i_dot             an int to indicate the father of a node
     * @param model             a DataModel that provides the methods necessary to retreive the countings
     *                          used in the calculations
     * @return                  returns a double with the score
     */
    @Override
    protected double calculateScore(int i, int i_dot, DataModel model) {
        int sonNode = i;
        int parentNode = i_dot;

        double qi = (double) model.getNumLabels(parentNode);
        double ri = (double) model.getNumLabels(sonNode);
        double s = (double) model.getNumClasses();
        double N = (double) model.getNCount();

        double sum = super.calculateScore(i, i_dot, model);
        sum = sum - ((s*(ri-1)*(qi-1)*Math.log(N))/2);

        return sum;
    }
}
