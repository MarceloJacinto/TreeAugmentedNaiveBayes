package graph.search;

/**
 * An interface that provides the methods to calculate the Minimum/Maximum spanning tree of a graph and return it in the
 * form of an array of integers where the content of the index i corresponds to j, the parent of i
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public interface MST {

    /**
     * A method that returns the Maximum/minimum spanning tree of a graph in array of integers. The content of the index i
     * corresponds to j, the parent of i.
     * @return      An array of integers with the tree
     */
    int[] getMST();
}
