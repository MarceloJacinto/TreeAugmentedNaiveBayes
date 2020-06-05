package graph;

import graph.edge.WeightedEdge;

import java.util.List;

/**
 * An interface that provides the methods to addEdge, removeEdge, get the number of vertices, ...
 * in a weighted graph. This interface uses a generic type that extends comparable.
 * @param <T>   a generic type for the class that is Comparable
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public interface WeightedGraph<T extends Comparable<T>> {

    /**
     * A method that given 2 integers with that symbolize the vertices that we want to connect and a weight
     * of generic type (that is comparable), adds the edge to the graph
     * @param source            An int with the index of the node
     * @param destination       An int with the index of the node
     * @param weight            A generic type (that is comparable) with a weight for the edge
     */
    void addEdge(int source, int destination, T weight);

    /**
     * A method to remove an existing edge from the graph given the index of the 2 connected nodes
     * @param source            An int with the index of the node
     * @param destination       An int with the index of the node
     */
    void removeEdge(int source, int destination);

    /**
     * A method to return the number of vertices that the graph has
     * @return              An integer with the number of nodes in the graph
     */
    int numVertices();

    /**
     * A method that returns a boolean indicating if the graph is directed or not
     * @return              A boolean that is true if the graph is directed or false if it is not
     */
    boolean isDirected();

    /**
     * A method that returns a list of WeightedEdges of generic type given their source node
     * (returns a list of edges that outward from a specific node)
     * @see WeightedEdge
     * @param source        An int with the index of the node
     * @return              A List of WeightedEdge
     */
    List<WeightedEdge<T>> getConnected(int source);
}
