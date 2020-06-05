package score;

import datamodel.DataModel;

/**
 * An interface that provides a method called score. This score method must receive a parentNode, a
 * fatherNode and a DataModel. The DataModel is necessary in order to get the countings necessary to the
 * score calculation.
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public interface Score {
    /**
     * A method that given a node index, the index of its father and a DataModel with countings returns
     * a double with a score for that particular connection between the node and its parent.
     * @param parentNode        an int to indicate the node itself
     * @param fatherNode        an int to indicate the father of a node
     * @param model             a DataModel that provides the methods necessary to retreive the countings
     *                          used in the calculations
     * @return                  returns a double with the calculated score
     */
    double score(int parentNode, int fatherNode, DataModel model);
}
