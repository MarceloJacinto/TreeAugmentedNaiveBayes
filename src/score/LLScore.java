package score;

import datamodel.DataModel;
import utils.Utils;

/**
 * A class that extends the AbstractScore class. It implements the method calculateScore by calculating the
 * Loglikelihood score for a node and it's parent given their indexes and a DataModel with countings from training data.
 * @see Score
 * @see AbstractScore
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public class LLScore extends AbstractScore {

    /**
     * A method that calculates a score given a node and it's parent index as well as a DataModel with countings
     * obtained from training data. The score is calculated according to the Loglikelihood formula.
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

        double qi = model.getNumLabels(parentNode);
        double ri = model.getNumLabels(sonNode);
        double s = model.getNumClasses();

        double N = model.getNCount();

        double sum = 0;

        for(int j = 0; j < qi; j++) {
            for(int k = 0; k < ri; k++) {
                for(int c = 0; c < s; c++) {

                    double N_ijkc = 0;
                    if (sonNode != parentNode) {
                        N_ijkc = (double) model.getNijkcCount(sonNode, parentNode, k, j, c);
                    } else {
                        N_ijkc = (double) model.getNikcCount(sonNode, k, c);
                    }

                    double N_c = (double) model.getNcCount(c);
                    double N_ikc = (double) model.getNikcCount(sonNode, k, c);
                    double N_ijc = (double) model.getNijcCount(sonNode, parentNode, j, c);

                    if(N_ijkc != 0) {
                        sum += (double) (N_ijkc / N) * Utils.log2((N_ijkc * N_c) / (N_ikc * N_ijc));
                    }

                }
            }
        }
        return sum;
    }



}
