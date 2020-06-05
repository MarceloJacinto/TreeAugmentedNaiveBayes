package graph;

import graph.edge.SimpleWeightedEdge;
import graph.edge.WeightedEdge;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * A class that implements the WeightedGraph interface. This class implements a graph with a fix number of nodes that is defined
 * in the constructor. This graph can be directed or not and this needs to be also specificied in the constructor arguments.
 * This implementation uses an Adjacency List to save the the edges between the nodes. Each node is represented by its index
 * (an integer). Each edge has a weight that is a generic type (that is also Comparable). This implementation of the graph does
 * not allow to save the data in the nodes. This is just a representation of the configuration of the data.
 * @see WeightedGraph
 * @see WeightedEdge
 * @see SimpleWeightedEdge
 * @param <T>   a generic type for the class that is Comparable
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public class SimpleWeightedGraph<T extends Comparable<T>> implements WeightedGraph<T> {

    /**
     * A boolean to specifity if the graph is directed or not
     */
    private boolean directed;

    /**
     * An int with the total number of vertices in the graph
     */
    private int numVertices;

    /**
     * The adjency list of Weidhted edges
     * @see graph.edge.WeightedEdge
     */
    private List<WeightedEdge<T>>[] adjcList;

    /**
     * Constructor for SimpleWeightedGraph. Received a boolean indicating if the graph is directed or not
     * and an integer with a number of vertices that will be fixed throughout the lifecycle of the object.
     * @param directed          A boolean indicating if the graph is directed or not
     * @param numVertices       An integer with the number of vertices the graph will contain
     */
    public SimpleWeightedGraph(boolean directed, int numVertices) {
       this.directed = directed;
       this.numVertices = numVertices;
       this.adjcList = new LinkedList[numVertices];

        /* Instantiate every list for each node */
        for(int i = 0; i < numVertices; i++) {
            adjcList[i] = new LinkedList<WeightedEdge<T>>();
        }

    }

    /**
     * A method to add edges to the graph, given the index of the destination and source nodes.
     * This method also receives a weight of generic type (as long as it is Comparable). The edges are saved
     * in an adjacency list and if the graph is directed another edge form destination to source is also added to the
     * adjacency list with the same weight.
     * @param source            An int with the index of the node
     * @param destination       An int with the index of the node
     * @param weight            A generic type (that is comparable) with a weight for the edge
     */
    @Override
    public void addEdge(int source, int destination, T weight) {

        WeightedEdge edge = new SimpleWeightedEdge(source, destination, weight);

        if(adjcList[source].contains(edge)) {
            adjcList[source].remove(edge);
            adjcList[source].add(edge);
        } else {
            adjcList[source].add(edge);
        }

        if(!directed) {
            WeightedEdge edge2 = new SimpleWeightedEdge(destination,source, weight);
            if(adjcList[destination].contains(edge2)) {
                adjcList[destination].remove(edge2);
                adjcList[destination].add(edge2);
            } else {
                adjcList[destination].add(edge2);
            }
        }
    }

    /**
     * A method that given the index of a source and destination node remove an edge (if exist) between
     * these 2 nodes.
     * @param source            An int with the index of the node
     * @param destination       An int with the index of the node
     */
    public void removeEdge(int source, int destination) {
        WeightedEdge edge =  new SimpleWeightedEdge(source, destination, null);
        if(adjcList[source].contains(edge))
            adjcList[source].remove(edge);

        if(!directed) {
            WeightedEdge edge2 = new SimpleWeightedEdge(destination, source, null);
            if(adjcList[destination].contains(edge2))
                adjcList[destination].remove(edge2);
        }
    }

    /**
     * A method that given the index of the source node returns the list of WeightedEdges that outward
     * from that node.
     * @see WeightedEdge
     * @param i         An int with the index of the node
     * @return          A List of WeightedEdge
     */
    public List<WeightedEdge<T>> getConnected(int i) {
        return this.adjcList[i];
    }

    /**
     * A method that returns whether the graph is directed or not
     * @return          A boolean that returns true if the graph is directed
     */
    @Override
    public boolean isDirected() {
        return directed;
    }

    /**
     * A method that returns the number of nodes/vertices in the graph
     * @return          An int with the number of nodes/vertices in the graph
     */
    @Override
    public int numVertices() {
        return numVertices;
    }

    /**
     * Redefinition of the toString() method in order to print if the graph is directed, the number of
     * vertices/nodes and the adjencyList of edges.
     * @return      A String with the weighted graph directed + numVertices + adjList
     */
    @Override
    public String toString() {
        return "WeightedGraph[" +
                "directed=" + directed +
                ", numVertices=" + numVertices +
                ", adjcList=" + Arrays.toString(adjcList) +
                ']';
    }

    /**
     * Redefinition of the equals() method. Uses the flag directed, the number of vertices and the adjency list
     * @param o     Another SimpleWeightedGraph object
     * @return      A boolean with true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleWeightedGraph)) return false;

        SimpleWeightedGraph<?> that = (SimpleWeightedGraph<?>) o;

        if (directed != that.directed) return false;
        if (numVertices != that.numVertices) return false;
        return Arrays.equals(adjcList, that.adjcList);
    }

    /**
     * Redefinition of the hashCode() method. Uses the flag directed, the number of vertices and the adjency list
     * @return      An int with the hashcode
     */
    @Override
    public int hashCode() {
        int result = (directed ? 1 : 0);
        result = 31 * result + numVertices;
        result = 31 * result + Arrays.hashCode(adjcList);
        return result;
    }
}
