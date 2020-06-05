package graph.edge;

import graph.WeightedGraph;

/**
 * An interface that provides the functionality of getting the weight of an edge, getting the source and getting its
 * destination. The weight of this edge is a generic type that must be (Comparable). The source and destionations
 * of the edge must be int.
 * @param <T>   A generic that extends Comparable
 * @see WeightedGraph
 * @see graph.SimpleWeightedGraph
 * @see WeightedEdge
 * @see SimpleWeightedEdge
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public interface WeightedEdge<T extends Comparable<T>> extends Comparable<WeightedEdge<T>> {

    /**
     * A method that when implemented should return the weight of an edge. This weight is a generic type that
     * is Comparable
     * @return      A generic type that is Comparable
     */
    T getWeight();

    /**
     * A method that when implemented should return the source (node) where the edge comes from
     * @return      An int with the source node of the edge
     */
    int getSource();

    /**
     * A method that when implemented should return the destination (node) where the edge goes to
     * @return      An int with the destination node of the edge
     */
    int getDestination();

}
