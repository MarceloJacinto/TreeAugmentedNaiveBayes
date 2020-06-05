package score;

import datamodel.DataModel;

/**
 * An abstract class that provides a method called score and a method called calculateScore. This class inherits the
 * interface Score. Any class that inherits this class must implement the method calculateScore which is responsible
 * for actually calculating the score.
 * @see Score
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public abstract class AbstractScore implements Score {

    /**
     * This method calls the abstract method calculateScore to actually calculate the score
     * @param parentNode        an int to indicate the node itself
     * @param fatherNode        an int to indicate the father of a node
     * @param model             a DataModel that provides the methods necessary to retreive the countings
     *                          used in the calculations
     * @return                  returns a double with the calculated score
     */
    @Override
    public double score(int parentNode, int fatherNode, DataModel model) {
        return calculateScore(parentNode, fatherNode, model);
    }

    /**
     * An abstract method that must be implement by a class that inherits the AbstractScore class. A method that given a
     * node index, the index of its father and a DataModel with countings returns a double with a score for that
     * particular connection between the node and its parent.
     * @param i                 an int to indicate the node itself
     * @param i_dot             an int to indicate the father of a node
     * @param model             a DataModel that provides the methods necessary to retreive the countings
     *                          used in the calculations
     * @return                  returns a double with the calculated score
     */
    protected abstract double calculateScore(int i, int i_dot, DataModel model);
}
