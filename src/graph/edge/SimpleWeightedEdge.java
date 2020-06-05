package graph.edge;

import graph.WeightedGraph;

/**
 * A class that implements the WeightedEdge interface. This class is an implementation of a weighted edge.
 * An instance of this class saves a weight of generic type (that extends Comparable), an integer with the source noce
 * and an int with the destination node. The weight of this edge is not just a Double or float but can be something
 * more complex, as some algorithms might need extra parameters with heuristics.
 * @param <T>       A generic that extends Comparable
 * @see WeightedGraph
 * @see graph.SimpleWeightedGraph
 * @see WeightedEdge
 * @see SimpleWeightedEdge
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public class SimpleWeightedEdge<T extends Comparable<T>> implements WeightedEdge<T> {

    /**
     * The weight of the edge - a generic type that extends comparable
     */
    private T weight;

    /**
     * An int with the source node
     */
    private int source;

    /**
     * An int with the destination node
     */
    private int destination;

    /**
     * Constructor for SimpleWeightedEdge, receives a source and destinations nodes as well a weight.
     * @param source        An int with the source node
     * @param destination   An int with the destination node
     * @param weight        A generic that extends comparable with the weight of the edge
     */
    public SimpleWeightedEdge(int source, int destination, T weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    /**
     * A method that returns the weight of the edge
     * @return      A generic that extends comparable with the weight of the edge
     */
    @Override
    public T getWeight() {
        return this.weight;
    }

    /**
     * A method that returns the source of the edge
     * @return      An int with the index of source node
     */
    @Override
    public int getSource() {
        return this.source;
    }

    /**
     * A method that returns the destination of the edge
     * @return      An int with the index of the destination node
     */
    @Override
    public int getDestination() {
        return this.destination;
    }

    /**
     * Redefinition of the compareTo method. An edge is compared based on the weight (that is a generic type that extends comparable)
     * using the weight compareTo method
     * @param o     Another WeightedEdge to compare against
     * @return      An int that is either negative, 0 or positive depending if the weight is lower, equal or greater
     */
    @Override
    public int compareTo(WeightedEdge o) {
        return getWeight().compareTo((T) o.getWeight());
    }

    /**
     * Redefinition of the toString method
     * @return      A String with the source, destination and weight of the edge
     */
    @Override
    public String toString() {
        return "WeightedEdge[" + "source=" + this.source + ", destination=" + this.destination + ", weight=" + weight.toString() + ']';
    }

    /**
     * Redefinition of the equals method to only take into consideration the source and destination edges
     * @param o     Another SimpleWeightedEdge to compare against
     * @return      A boolean that it is true if the SimpleWeightedEdge 's have the same source and destination nodes
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleWeightedEdge)) return false;

        SimpleWeightedEdge<?> that = (SimpleWeightedEdge<?>) o;

        if (source != that.source) return false;
        return destination == that.destination;
    }

    /**
     * Redefinition of the hashCode method
     * @return  An int with the hashcode
     */
    @Override
    public int hashCode() {
        int result = source;
        result = 31 * result + destination;
        return result;
    }
}
