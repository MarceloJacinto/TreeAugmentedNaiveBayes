package graph.edge;

import graph.WeightedGraph;

import java.util.Comparator;

/**
 * A class that implements the Comparator interface for the SimpleWeightedEdge. This class is used to define a comparator
 * that is the inverse of the natural order of the an Edge weight. This might be usefull when ordering edges in ascending order
 * @see WeightedGraph
 * @see graph.SimpleWeightedGraph
 * @see WeightedEdge
 * @see SimpleWeightedEdge
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public class ReverseComparatorByWeightedEdge implements Comparator<SimpleWeightedEdge> {

    /**
     * A method that compares 2 SimpleWeightedEdges using the inverse order defined in the compareTo() method
     * implemented in the SimpleWeightedEdge class
     * @param o1        A SimpleWeightedEdge
     * @param o2        A SimpleWeightedEdge
     * @return          an int that is negative, zero or positive if the value is greater, equal or smaller than each other
     */
    @Override
    public int compare(SimpleWeightedEdge o1, SimpleWeightedEdge o2) {
        return -o1.compareTo(o2);
    }
}
