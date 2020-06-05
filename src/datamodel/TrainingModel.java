package datamodel;

import classifier.TreeAugmentedNayveBayes;
import datamodel.categories.Category;
import datamodel.categories.ModelCategory;
import datamodel.classifications.Classification;
import datamodel.classifications.ModelClassfication;
import exceptions.InvalidNumberOfFeaturesException;
import exceptions.RepeatedLabelException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * A class that implements the DataModel interface. This particular model is very useful for a treeAugmentedNaiveBayes
 * classifier.
 * @param <T>       A generic type
 * @see DataModel
 * @see TreeAugmentedNayveBayes
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-06
 */
public class TrainingModel<T> implements DataModel<T> {

    /**
     * The total number of examples extracted from training data
     */
    protected int N = 0;

    /**
     * The Laplace Smoothing term to be used with this data
     */
    protected final double N_plica = 0.5;

    /**
     * A list of categories, with their respective counts and information. Example X1 and
     * its counts for Nijkc, Nikc, Nijc, the labels,...
     * @see datamodel.categories.Category
     */
    protected Category<T>[] categories;

    /**
     * A list of classifications with their respective labels, etc
     * @see datamodel.classifications.Classification
     */
    protected Classification<T> classes;

    /**
     * The constructor for the TrainingModel class. Receives an array of String of labels
     * to be considered. Example: X1, X2, X3
     * @param labels        An array of String with labels
     */
    public TrainingModel(String[] labels) {
        categories = new Category[labels.length];

        /* Check for repeated labels. If they exist throw an error */
        for(int i = 0; i < labels.length; i++) {
            for(int j = i+1; j < labels.length; j++) {
                if(labels[i].equals(labels[j])) throw new RepeatedLabelException("A label is repeated. This is not allowed");
            }
        }

        for(int i = 0; i < categories.length; i++) categories[i] = new ModelCategory<T>(labels[i], labels.length);

        classes = new ModelClassfication<T>();
    }

    /**
     * A method that updates the countings in the DataModel given an array of generic type with data
     * @param data      An array of generic type
     */
    @Override
    public void updateModel(T[] data) {

        //Check if the data has the right size. If not, throw an exception
        if(data.length != categories.length + 1) throw new InvalidNumberOfFeaturesException("The training data you are feeding me" +
                "does not correspond to the number of labels specified + a class");

        //Update the number of training examples
        N++;

        //For each category: X1, X2, X3 check if the label that it assumes already exists
        //and save it if it does not
        for(int i = 0; i < categories.length; i++) categories[i].addLabelIfNotExistent(data[i]);

        //Check if the label the class assumes already exists and save it if does not
        classes.addLabelIfNotExistent(data[data.length-1]);

        //Update the N_c count
        classes.incrementNcCount(data[data.length-1]);

        // Generate the more complicated countings

        int c = classes.getLabelIndex(data[data.length-1]);
        //For each category, consider a son
        for(int i = 0; i < categories.length; i++) {

            int k = categories[i].getLabelIndex(data[i]);

            //Update the Nikc count
            categories[i].incrementNikc(i, k, c);

            //For each category, consider a father
            for(int f = 0; f < categories.length; f++) {
                //The father can never be himself
                if( f!= i ) {
                    int j = categories[f].getLabelIndex(data[f]);

                    //Update the Nijkc count for the son
                    categories[i].incrementNijkc(i, j, k, c, f);

                    //Update the Nijc count for the son
                    categories[i].incrementNijc(i, j, c, f);
                }
            }
        }
    }

    /**
     * A method that updates the counting in the DataModel given a list of arrays of generic type with data
     * @param data      A List of arrays of generic type
     */
    @Override
    public void batchUpdateModel(List<T[]> data) {
        Iterator<T[]> it = data.iterator();
        while(it.hasNext()) {
            T[] lineData = it.next();
            updateModel(lineData);
        }
    }

    /**
     * A method that returns the number of labels a given node has given the node index to be considered.
     * Example: Node 0 (X0) contains 3 values: 0, 1, 2
     * @param parentNode        The index of the node to be considered
     * @return                  The number of labels that node has
     */
    @Override
    public int getNumLabels(int parentNode) {
        return categories[parentNode].getNumLabels();
    }

    /**
     * A method that returns the number of classes that the DataModel can assume.
     * Example: Class can vary from: 0 - 19 - In that case the method should return 20
     * @return                  An int with the number of classes that the data can assume
     */
    @Override
    public int getNumClasses() {
        return classes.getNumLabels();
    }

    /**
     * A method that returns the number of categories that a DataModel has
     * Example: We can have X1, X2 and X3 - In that case the method should return 3
     * @return      An int with the number of categories used in the DataModel
     */
    @Override
    public int getNumCategories() {
        return categories.length;
    }

    /**
     * A method that when implemented returns the LaplaceSmoothingTerm used (by default could be 0.5)
     * @return      A double with the LaplaceSmoothingTerm
     */
    @Override
    public double getLaplaceSmoothingTerm() {
        return N_plica;
    }

    /**
     * A method that returns the index of a label of a given category
     * Example: We want to know the index of the label 3 in X2 - In this case it could return 2
     * @param category      An int with the index of the category being considered
     * @param label         A generic type with the label we want to get the index of
     * @return              An int with the index of that label
     */
    @Override
    public int getIndexOfCategoryLabel(int category, T label) {
        return categories[category].getIndexOfLabel(label);
    }

    /**
     * A method that returns a label for a given index of a class.
     * Example: We want to know the label of classification stored at index 0 - In this case it could return "apple"
     * @param c     An int with the index of the classification
     * @return      A generic type with the classification
     */
    @Override
    public T getClassLabel(int c) {
        return classes.getLabelOfIndex(c);
    }

    /**
     * A method that returns an int with a count for a given N_ijkc
     * @param sonNode       An int with the index of the node we are considering
     * @param parentNode    An int with the index of the father of that node
     * @param k             An int with the configuration of the node
     * @param j             An int with the configuration of the father of the node
     * @param c             An int with the classification
     * @return              An int with the number of occurences of that combination
     */
    @Override
    public int getNijkcCount(int sonNode, int parentNode, int k, int j, int c) {
        return categories[sonNode].getNijkc(sonNode, j, k, c, parentNode);
    }

    /**
     * A method that returns an int with a count for a given N_ikc
     * @param sonNode       An int with the index of the node we are considering
     * @param k             An int with the configuration of the node
     * @param c             An int with the classification
     * @return              An int with the number of occurences of that combination
     */
    @Override
    public int getNikcCount(int sonNode, int k, int c) {
        return categories[sonNode].getNikc(sonNode, k, c);
    }

    /**
     * A method that returns an int with a count for a given N_ijc
     * @param sonNode       An int with the index of the node we are considering
     * @param parentNode    An int with the index of the father of that node
     * @param j             An int with the configuration of the father of the node
     * @param c             An int with the classification
     * @return              An int with the number of occurences of that combination
     */
    @Override
    public int getNijcCount(int sonNode, int parentNode, int j, int c) {
        return categories[sonNode].getNijc(sonNode, j, c, parentNode);
    }

    /**
     * A method that returns an int with a count for a given N_c
     * @param c             An int with the classification
     * @return              An int with the number of occurences of that combination
     */
    @Override
    public int getNcCount(int c) {
        return classes.getNcCount(c);
    }

    /**
     * A method that returns an int with the number of training examples used
     * @return              An int with the number of training examples
     */
    @Override
    public int getNCount() {
        return this.N;
    }

    /**
     * A method that, given the index of a category (ex. X1, X2, X3) should return the
     * Category object with all the countings for that specific category
     * @param i         The index of the category
     * @return          A Category object
     */
    public Category<T> getCategory(int i) {
        return categories[i];
    }

    /**
     * Redefinition of the toString() method. Prints all the data relative to number of examples, categories and classes.
     * @return      A String with the relevant data
     */
    @Override
    public String toString() {
        return "TrainingModel{" +
                "N=" + N +
                ", N_plica=" + N_plica +
                ", categories=" + Arrays.toString(categories) +
                ", classes=" + classes +
                '}';
    }

    /**
     * Redefinition of the equals() method, using the values of N_plica, categories and classifications
     * to make the comparison
     * @param o     Another TrainingModel
     * @return      A boolean with true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainingModel)) return false;

        TrainingModel<?> that = (TrainingModel<?>) o;

        if (N != that.N) return false;
        if (Double.compare(that.N_plica, N_plica) != 0) return false;
        if (!Arrays.equals(categories, that.categories)) return false;
        return classes.equals(that.classes);
    }

    /**
     * Redefinition of the hashCode() method using the N_plica, categories and classifications
     * @return      An int with the hashcode
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = N;
        temp = Double.doubleToLongBits(N_plica);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + Arrays.hashCode(categories);
        result = 31 * result + classes.hashCode();
        return result;
    }
}
