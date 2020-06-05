package graph.search;

import graph.WeightedGraph;
import graph.edge.SimpleWeightedEdge;
import graph.edge.WeightedEdge;

/**
 * An abstract class called AbstractMST (Maximum Spanning Tree). This class implements the MST interface .This abstract class is a bluePrint for the implementation of
 * an algorithm that given a WeightedGraph calculates the Maximum Weighted Spanning Tree and returns it in the form of
 * an array of integers with the content of the array being like tree[i] = parent_of_i.
 * @see MST
 * @see KruskalMST
 * @see WeightedGraph
 * @see graph.SimpleWeightedGraph
 * @see WeightedEdge
 * @see SimpleWeightedEdge
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public abstract class AbstractMST implements MST{

    /**
     * A Weighted graph to calculate the MST from
     * @see graph.WeightedGraph
     */
    protected WeightedGraph graph;

    /**
     * The constructor for the AbstractMST. Receives a WeightedGraph as argument
     * @see WeightedGraph
     * @param graph         A WeightedGraph that is already set
     */
    public AbstractMST(WeightedGraph graph) {
        this.graph = graph;
    }

    /**
     * A method that returns the Maximum weighted Spanning tree. Inside it calls the method
     * calculateMST().
     * @return      An int[] with the format tree[i] = parent_of_i.
     */
    @Override
    public int[] getMST() {
        return calculateMST();
    }

    /**
     * An abstract method to be implemented a posteriori that calculates the the maximum weighted spanning tree
     * from the graph passed in the constructor
     * @return      returns an array with int with the tree
     */
    protected abstract int[] calculateMST();

    /**
     * Returns the graph used to construct the MST.
     * @see WeightedGraph
     * @return      returns WeightedGraph
     */
    protected WeightedGraph getGraph(){
        return this.graph;
    }

}
