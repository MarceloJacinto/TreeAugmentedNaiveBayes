package metrics.perclass;

/**
 * This is an interface for a singleScore. That is, given an index of a class, get the score for that particular class.
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
public interface SingleScore {

    /**
     * A method that when given the index of a class should return a double with the corresponding score for that class
     * @param i     An int with the index of the class
     * @return      A double with a score
     */
    double getScore(int i);

}
