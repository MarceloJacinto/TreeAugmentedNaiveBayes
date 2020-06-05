package graph.search;

import graph.WeightedGraph;
import graph.edge.ReverseComparatorByWeightedEdge;
import graph.edge.SimpleWeightedEdge;
import graph.edge.WeightedEdge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * A class that extends MST and implements the Kruskal Algorithm to calculate a Maximal Weighted Spanning Tree
 * @see AbstractMST
 * @see WeightedGraph
 * @see graph.SimpleWeightedGraph
 * @see WeightedEdge
 * @see SimpleWeightedEdge
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public class KruskalMST extends AbstractMST {

    /**
     * The constructor that receives a WeightedGraph as parameter to be used to calculate the MST. The WeightedGraph
     * must be set when received.
     * @see WeightedGraph
     * @param graph     A WeightedGraph
     */
    public KruskalMST(WeightedGraph graph) {
        super(graph);
    }

    /**
     * The method that implements the calculation of the MST tree from the graph received as parameter in the constructor
     * Using the Kruskal algorithm.
     * This method also makes use of private auxiliary functions such as: recursiveFind, generateTree and orderIt.
     * @return      An array of int[] with the format tree[i] = parent_of_i (representing the tree).
     *              The nodes that have no parent node (are roots) start have a -1.
     */
    @Override
    protected int[] calculateMST() {

        PriorityQueue<SimpleWeightedEdge> orderedEdges = new PriorityQueue<SimpleWeightedEdge>(new ReverseComparatorByWeightedEdge());
        List<SimpleWeightedEdge> maximumSpanningTree = new ArrayList<SimpleWeightedEdge>();

        for(int i = 0; i < this.graph.numVertices(); i++) {
            Iterator<WeightedEdge> it = this.graph.getConnected(i).iterator();
            while(it.hasNext()) {
                SimpleWeightedEdge ed = (SimpleWeightedEdge) it.next();
                orderedEdges.add(ed);
            }
        }

        //Create a parent array
        int[] parent = new int[this.graph.numVertices()];
        for(int i = 0; i < this.graph.numVertices(); i++) {
            parent[i] = i;
        }

        int index = 0;
        while(index < this.graph.numVertices()-1) {
            SimpleWeightedEdge ed = orderedEdges.remove();

            //check for circle closures
            int x = recursiveFind(parent, ed.getSource());
            int y = recursiveFind(parent, ed.getDestination());

            // if x==y we create a circle, otherwise we don't
            if(x != y) {
                maximumSpanningTree.add(ed);
                index++;
                parent[y] = x; // Make the connection
            }
        }
        return generateTree(maximumSpanningTree);
    }

    /**
     * An auxiliary method to check if there is a loop closure between a parent an a node
     * @param parent        an array of int that contains the parent of each node
     * @param node          an int with the node that we want to add to the tree
     * @return              returns an int with the first parent ("the root" - not the most accurate description, but
     *                      it an be considered the root until this point)
     */
    private int recursiveFind(int[] parent, int node) {
        //chain of parent pointers from x upwards through the tree
        // until an element is reached whose parent is itself
        if(parent[node]!=node)
            return recursiveFind(parent, parent[node]);;
        return node;
    }

    /**
     * An auxiliary method to generate a tree given a list of SimpleWeightedEdges that belong to the MST.
     * This algorithm assumes that the root node is always the first element of the array (int 0). The root has the
     * value -1 in the array.
     * @see SimpleWeightedEdge
     * @param edges         A List of SimpleWeightedEdge edges that belong to the tree
     * @return              A tree with a root and with all vertices that outward from the root
     */
    private int[] generateTree(List<SimpleWeightedEdge> edges) {
        int[] tree = new int[this.graph.numVertices()];
        for(int i = 0; i < this.graph.numVertices(); i++) {
            tree[i] = i;
        }
        tree[0] = -1; //root

        orderIt(edges, tree, 0); //get an array of parents

        //Check if a node does not have a parent. If it does not, put a minus one
        //as it is a root - just for safety
        for(int i = 0; i < tree.length; i++) {
            if (tree[i] == i) tree[i] = -1;
        }

        //TO BE REMOVED
        /*for(int i = 0; i < tree.length; i++) System.out.print(tree[i] + " ");
        System.out.print("\n");*/
        return tree;
    }

    /**
     * An auxiliary method that given a list of edges, an array that is going to be the final tree and
     * an integer symbolizing the index of the parent in the array checks if the node already has a parent. If not
     * create change it and add a parent to it (if there is an edge connecting the node to another node)
     * @see SimpleWeightedEdge
     * @param edges     A list of SimpleWeightedEdge with the edges that form the MST
     * @param tree      An array of int that is going to encode the final tree
     * @param parent    A node to consider as parent of other nodes
     */
    private void orderIt(List<SimpleWeightedEdge> edges, int[] tree, int parent) {
        Iterator<SimpleWeightedEdge> it = edges.iterator();
        while(it.hasNext()) {
            SimpleWeightedEdge ed = it.next();
            if(ed.getSource() == parent && tree[ed.getDestination()] == ed.getDestination()) {
                /*System.out.println("run");*/
                tree[ed.getDestination()] = parent;        //indicate the parent in the array
                orderIt(edges, tree, ed.getDestination()); // call the function on itself
            } else if(ed.getDestination() == parent && tree[ed.getSource()] == ed.getSource()) {
                /*System.out.println("run");*/
                tree[ed.getSource()] = parent;
                orderIt(edges, tree, ed.getSource());
            }
        }
    }
}