package classifier;

import datamodel.DataModel;
import exceptions.InvalidNumberOfFeaturesException;
import graph.SimpleWeightedGraph;
import graph.search.KruskalMST;
import graph.search.MST;
import score.Score;
import utils.Utils;

import java.util.*;

/**
 * A class that extends the AbstractBayes class. This class implements the Tree Augmented Nayve Bayes algorithm.
 * @param <T>       A generic type
 * @see Classifier
 * @see AbstractClassifier
 * @see AbstractBayes
 * @see score.Score
 * @see score.MDLScore
 * @see score.LLScore
 * @see datamodel.DataModel
 * @see datamodel.TrainingModel
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public class TreeAugmentedNayveBayes<T> extends AbstractBayes<T> {

    /**
     * An hashmap to save the calculations of the theta_ijkc
     */
    protected HashMap<String, Double> theta_ijkc;
    /**
     * An list of double to save the values of theta_c
     */
    protected double[] theta_c;
    /**
     * An int array that inside contains the parent of each node. If inside there is a -1
     * it means the node of the respective index has no parent node. Example: int[0] = -1
     * means node 0 has no parent node.
     */
    protected int tree[];

    /**
     * The constructor for the TreeAugmentedNayveBayes class. It receives as parameters a Score and a Datamodel
     * @param score             A Score to be used to calculate the weight between the edges of the tree
     * @param trainingModel     A DataModel with the counts extrated from the training data
     * @see score.Score
     * @see score.MDLScore
     * @see score.LLScore
     * @see datamodel.DataModel
     * @see datamodel.TrainingModel
     */
    public TreeAugmentedNayveBayes(Score score, DataModel<T> trainingModel) {
        super(score, trainingModel);
        theta_ijkc = new HashMap<String, Double>();
    }

    /**
     * A method that implements the training method. This method only invokes inside the learnStructure() and
     * learnParameters() methods inside
     */
    @Override
    protected void doTrain() {
        learnStructure();
        learnParameters();
    }

    /**
     * A method that makes a prediction when receives an array of data of generic type. This method invokes inside the
     * doPredictOne() method
     * @param data      An array of generic type to make predictions with
     * @return          A generic with the result of the prediction
     */
    @Override
    protected T doPredict(T[] data) {
        return doPredictOne(data);
    }

    /**
     * This method uses the data stored in the trainingModel and the Score to calculate the MST (maximal weighted spanning tree).
     * The structure is then saved in the tree attribute of this class. The content of the array is the index of the parent node
     * of each node corresponding to that index. If the content of some node is -1 it means that node is a root.
     */
    protected void learnStructure() {
        /* Create the graph that will give us a tree */
        int numCategories = trainingModel.getNumCategories();

        SimpleWeightedGraph graph = new SimpleWeightedGraph(false, numCategories);
        for(int i = 0; i < numCategories; i++) {
            for(int j = i+1; j < numCategories; j++) {
                graph.addEdge(i, j, score.score(i, j, trainingModel));
            }
        }

        /* Calculate the minimum spanning tree */
        MST maximumSpanningTree = new KruskalMST(graph);

        this.tree = maximumSpanningTree.getMST();
    }

    /**
     * This method calculates the values for the conditional probabilities for the tree structure saved in the
     * attribute tree. The values for the conditional probabilities are saved in the hashMap "theta_ijkc" and in the double array "theta_c"
     */
    protected void learnParameters() {
        this.theta_c = new double[trainingModel.getNumClasses()];

        int numCategories = trainingModel.getNumCategories();
        int s = trainingModel.getNumClasses();
        double N_plica = trainingModel.getLaplaceSmoothingTerm();

        /* calculate the theta_ijkc */
        for(int i = 0; i < numCategories; i++) {
            int parentNode = tree[i];
            int ri = trainingModel.getNumLabels(i);

            if(parentNode != -1) {
                int qi = trainingModel.getNumLabels(parentNode);

                for(int j = 0; j < qi; j++) {
                    for(int k = 0; k < ri; k++) {
                        for(int c = 0; c < s; c++) {
                            double N_ijkc = (double) trainingModel.getNijkcCount(i, parentNode, k, j, c);
                            double N_ijc = (double) trainingModel.getNijcCount(i, parentNode, j, c);

                            double theta = (N_ijkc + N_plica) / (N_ijc + (double) (ri * N_plica));
                            String key = Utils.keyGenerator(i, j, k, c);
                            this.theta_ijkc.put(key, theta);
                        }
                    }
                }
            } else {
                int j = i; //we choose j == i when we don't have a parent to be coeherent with the algorithm description
                for(int k = 0; k < ri; k++) {
                    for(int c = 0; c < s; c++) {
                        // If we have no parent, Nijkc is in reality Nikc
                        double N_ijkc = (double) trainingModel.getNikcCount(i,k,c);
                        // If we have no parent, Nijc is in reality Nc
                        double N_ijc = (double) trainingModel.getNcCount(c);

                        double theta = (N_ijkc + N_plica) / (N_ijc + (double) (ri * N_plica));
                        String key = Utils.keyGenerator(i, j, k, c);
                        theta_ijkc.put(key, theta);
                    }
                }
            }
        }

        /* calculate theta_c */
        for(int c = 0; c < s; c++) {
            double N_c = trainingModel.getNcCount(c);
            theta_c[c] = (double) (N_c + N_plica) / (trainingModel.getNCount() + (s*N_plica));
        }
    }

    /**
     * This method makes a prediction by calculating the multiplication of the conditional probabilities. In reality if calculates
     * the sum of the logarithm of the conditional probabilities to avoid numerical problems. Then it choosen the class with the highest
     * probability and returns it. If data[i] contains a value that is not in the DataModel, it returns null
     * @param data      An array of generic type with data to make the prediction with
     * @return          A generic with the prediction
     */
    protected T doPredictOne(T[] data) {

        //Check if the data has the same size as the number of features used to train
        if(data.length != trainingModel.getNumCategories())
            throw new InvalidNumberOfFeaturesException("The number of features in the data does not correspond to the number of features considered when training");

        /* Get the configuartion of each class */
        int[] configurations = new int[trainingModel.getNumCategories()];
        for(int i = 0; i < configurations.length; i++) {
            try {
                configurations[i] = trainingModel.getIndexOfCategoryLabel(i, data[i]);
            } catch (IndexOutOfBoundsException e) {
                /* In this case, we found a value in the test set that was never found before. Just use another value from
                that feature for the prediction (better have something than nothing). Example: if X1 had the value 2, but
                during training only appeared values of 0 and 1, then "virtualy replace" the 2 we just saw with a 0
                 * If that feature never had a value, a big error occured and just return null - do not recover from it...
                 */
                try {
                    configurations[i] = trainingModel.getIndexOfCategoryLabel(i, trainingModel.getCategory(i).getLabelOfIndex(0));
                } catch(IndexOutOfBoundsException e2) {
                    return null;
                }
            }
        }

        /* Get the probability of each class individually */
        int s = trainingModel.getNumClasses();
        double[] probabilityOfEachClass = new double[s];
        for(int c = 0; c < s; c++) {
            probabilityOfEachClass[c] = Math.log(theta_c[c]);

            for(int i = 0; i < trainingModel.getNumCategories(); i++) {

                int parentNode = tree[i];
                int k = configurations[i];
                int j = 0;

                //Check if the node has a parent or not and choose j accordingly
                j = (parentNode == -1) ? i : configurations[parentNode];

                String key = Utils.keyGenerator(i, j, k, c);
                if(theta_ijkc.containsKey(key)) {
                    probabilityOfEachClass[c] = probabilityOfEachClass[c] + Math.log(theta_ijkc.get(key));
                } else {
                    /* if we can not find that theta, its count was zero*/
                    /* this is equivalent to multiply by zero for that class - log would be -infity */
                    probabilityOfEachClass[c] = Double.NEGATIVE_INFINITY;
                }
            }
        }

        /* Get the class with the highest score */
        int max = Utils.findMaxIndex(probabilityOfEachClass);

        return trainingModel.getClassLabel(max);
    }

    /**
     * Redefinition of the toString() method that returns a string with the tree structure of the classifier
     * @return      A String with the tree structure of the classifier
     */
    @Override
    public String toString() {
        String s = "Classifier: ";
        boolean firstLine = true;
        for(int i = 0; i < this.tree.length; i++) {
            if(firstLine == false) s += "\n\t    ";
            if(tree[i] != -1) {
                s += this.trainingModel.getCategory(i).getCategoryName() + " : class " + this.trainingModel.getCategory(tree[i]).getCategoryName();
            }else {
                s += this.trainingModel.getCategory(i).getCategoryName() + " : class";
            }
            firstLine = false;
        }
        s += "\n\t    class :";
        return s;
    }

    /**
     * Redefinition of the equals() method. Compares a TreeAugmentedNaiveBayes based on the values of the tree,
     * the theta_ijkc and theta_c
     * @param o     Another TreeAugmentedNayveBayes
     * @return      A boolean true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TreeAugmentedNayveBayes)) return false;

        TreeAugmentedNayveBayes<?> that = (TreeAugmentedNayveBayes<?>) o;

        if (!theta_ijkc.equals(that.theta_ijkc)) return false;
        if (!Arrays.equals(theta_c, that.theta_c)) return false;
        return Arrays.equals(tree, that.tree);
    }

    /**
     * Redefinition of the hashCode() method using the values of the tree, theta_ijkc and theta_c
     * @return      An int with the hashcode
     */
    @Override
    public int hashCode() {
        int result = theta_ijkc.hashCode();
        result = 31 * result + Arrays.hashCode(theta_c);
        result = 31 * result + Arrays.hashCode(tree);
        return result;
    }
}
