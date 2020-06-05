package main.tests;

import graph.SimpleWeightedGraph;
import graph.WeightedGraph;
import graph.search.KruskalMST;
import graph.search.AbstractMST;

/**
 * CLASS USED ONLY FOR TESTING PURPOSES - THIS IS BASICALLY A UNIT TEST OF THE CODE
 * A class used to test the SimpleWeightedGraph class
 */
public class TestGraph {
    public static void main(String[] args) {
        WeightedGraph grafo = new SimpleWeightedGraph(false, 9);
        grafo.addEdge(0,1,4);
        grafo.addEdge(0,7,8);
        grafo.addEdge(1,7,11);
        grafo.addEdge(1,2,8);
        grafo.addEdge(7,8,7);
        grafo.addEdge(7,6,1);

        grafo.addEdge(2,3,7);
        grafo.addEdge(2,5,4);
        grafo.addEdge(2,8,2);

        grafo.addEdge(8,6,6);
        grafo.addEdge(6,5,2);

        grafo.addEdge(3,5,14);
        grafo.addEdge(3,4,9);
        grafo.addEdge(5,4,10);

        AbstractMST maximumSpanningTree = new KruskalMST(grafo);
        //grafo.removeEdge(0,3);

        int [] treeStruct = maximumSpanningTree.getMST();
        for(int i = 0; i < treeStruct.length; i++) {
            System.out.print(treeStruct[i] + " ");
        }
    }
}
