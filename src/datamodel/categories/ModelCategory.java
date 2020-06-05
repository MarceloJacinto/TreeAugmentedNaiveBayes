package datamodel.categories;

import classifier.TreeAugmentedNayveBayes;
import utils.Utils;

import java.util.*;

/**
 * A class that implements the Category interface. Saves the counts for the data in arrays of hashmaps and hashmaps.
 * Saves a list of labels, the category name and the total number of labels
 * @param <T>       A generic type
 * @see datamodel.Model
 * @see datamodel.categories.Category
 * @see datamodel.classifications.ModelClassfication
 * @see TreeAugmentedNayveBayes
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-06
 */
public class ModelCategory<T> implements Category<T> {

    /**
     * The name of the category: ex. X1
     */
    protected String categoryName;

    /**
     * The total number of different labels this category has: example- 0,1,2 - totalNumberLabels = 3
     */
    protected int totalNumberLabels;

    /**
     * A list with all the labels that were observed: example: 0, 1, 2
     */
    protected List<T> labels;

    /**
     * An array of HashMap that considers in each position of the array a diferent parent for the current node
     * Saves the N_ijkc count, and its indexed by a String of type "i,j,k,c" in the correct hashmap depending on
     * the parent of the node
     */
    protected HashMap<String, Integer>[] N_ijkc;

    /**
     * An array of HashMap that considers in each position of the array a diferent parent for the current node
     * Saves the N_ijc count, and its indexed by a String of type "i,j,c" in the correct hashmap depending on
     * the parent of the node
     */
    protected HashMap<String, Integer>[] N_ijc;

    /**
     * An HashMap that save the N_ikc count, and its indexed by a String of type "i,k,c"
     */
    protected HashMap<String, Integer> N_ikc;

    /**
     * The constructor for the ModelCategory. Receives as parameters a string with the name of the category and
     * the number of categories that is going to be considered (in order to instantiate the arrays that relates this category
     * with others)
     * @param categoryName      A String with the category name : Ex("Color")
     * @param numCategories     An int with the total number of categories - Ex.3 (Color, shape, age)
     */
    public ModelCategory(String categoryName, int numCategories) {
        this.categoryName = categoryName;
        totalNumberLabels = 0;
        labels = new LinkedList<T>();

        N_ijkc = new HashMap[numCategories];
        N_ijc = new HashMap[numCategories];
        N_ikc = new HashMap<String, Integer>();

        for(int i = 0; i < numCategories; i++){
            N_ijkc[i] = new HashMap<String, Integer>();
            N_ijc[i] = new HashMap<String, Integer>();
        }
    }

    /**
     * A method that should return the category name (ex: "Color")
     * @return      A String with the category name
     */
    @Override
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * A method that should return the index of a certain label of the category (ex: index of the label "green")
     * @param label     A generic type with the label
     * @return          An int with the index
     */
    @Override
    public int getIndexOfLabel(T label) {
        int i = labels.indexOf(label);
        if(i == -1) throw new IndexOutOfBoundsException("There exist only " + labels.size() + " labels");
        return i;
    }

    /**
     * A method that returns the total number of labels for this category
     * @return      An int with the total number of labels
     */
    @Override
    public int getNumLabels() {
        return totalNumberLabels;
    }

    /**
     * A method that return the list of labels saved for this category
     * @return      A list of generic type with the labels
     */
    @Override
    public List<T> getAllLabels() {
        return labels;
    }

    /**
     * A method that adds a label to the category if it does not exist there yet
     * @param data      A generic type with a label to be saved
     * @return          A boolean that returns true if it was added to the list
     */
    @Override
    public boolean addLabelIfNotExistent(T data) {
        if(!labels.contains(data)) {
            labels.add(data);
            totalNumberLabels++;
            return true;
        }
        return false;
    }

    /**
     * A method that should return the index of a certain label of the category (ex: index of the label "green")
     * @param data      A generic type with a label
     * @return          An int with the index
     */
    @Override
    public int getLabelIndex(T data) {
        return labels.indexOf(data);
    }

    /**
     * A method that should return a label given its index
     * @param i         An int with the index of a given label
     * @return          A generic type with the label
     */
    @Override
    public T getLabelOfIndex(int i) {
        if(i >= labels.size()) throw new IndexOutOfBoundsException("There are only " + labels.size() + " labels");
        return labels.get(i);
    }

    /**
     * A method that should return the N_ijkc counts
     * @param i             An int with the node index that we are considering
     * @param j             An int with the configuration of the parent of that node
     * @param k             An int with the configuration of the node
     * @param c             An int with the configuration of the classification
     * @param parentNode    An int with the parent of the node
     * @return              An int with the counts for that combination
     */
    @Override
    public int getNijkc(int i, int j, int k, int c, int parentNode) {
        String key = Utils.keyGenerator(i, j, k, c);

        if(N_ijkc[parentNode].containsKey(key)) return N_ijkc[parentNode].get(key);
        return 0;
    }

    /**
     * A method that should return the N_ikc count
     * @param i             An int with the node index that we are considering
     * @param k             An int with the configuration of the node
     * @param c             An int with the configuration of the classification
     * @return              An int with the counts for that combination
     */
    @Override
    public int getNikc(int i, int k, int c) {
        String key = Utils.keyGenerator(i, k, c);

        if(N_ikc.containsKey(key)) return N_ikc.get(key);
        return 0;
    }

    /**
     * A method that should return the N_ijc count
     * @param i             An int with the node index that we are considering
     * @param j             An int with the configuration of the parent of that node
     * @param c             An int with the configuration of the classification
     * @param parentNode    An int with the parent of the node
     * @return              An int with the counts for that combination
     */
    @Override
    public int getNijc(int i, int j, int c, int parentNode) {
        String key = Utils.keyGenerator(i, j, c);

        if(N_ijc[parentNode].containsKey(key)) return N_ijc[parentNode].get(key);
        return 0;
    }

    /**
     * A method that should increment the Nikc count
     * @param i             An int with the node index that we are considering
     * @param k             An int with the configuration of the node
     * @param c             An int with the configuration of the classification
     */
    @Override
    public void incrementNikc(int i, int k, int c) {
        String key = Utils.keyGenerator(i, k, c);

        if(N_ikc.containsKey(key)) {
            int count = N_ikc.get(key);
            count += 1;
            N_ikc.replace(key, count);
        } else {
            N_ikc.put(key, 1);
        }
    }

    /**
     * A method that should increment the Nijkc count
     * @param i             An int with the node index that we are considering
     * @param j             An int with the configuration of the parent of that node
     * @param k             An int with the configuration of the node
     * @param c             An int with the configuration of the classification
     * @param parentNode    An int with the parent of the node
     */
    @Override
    public void incrementNijkc(int i, int j, int k, int c, int parentNode) {
        String key = Utils.keyGenerator(i, j, k, c);
        if(N_ijkc[parentNode].containsKey(key)) {
            int count = N_ijkc[parentNode].get(key);
            count += 1;
            N_ijkc[parentNode].replace(key, count);
        } else {
            N_ijkc[parentNode].put(key, 1);
        }
    }

    /**
     * A method that should increment the Nijc count
     * @param i             An int with the node index that we are considering
     * @param j             An int with the configuration of the parent of that node
     * @param c             An int with the configuration of the classification
     * @param parentNode    An int with the parent of the node
     */
    @Override
    public void incrementNijc(int i, int j, int c, int parentNode) {
        String key = Utils.keyGenerator(i, j, c);
        if(N_ijc[parentNode].containsKey(key)) {
            int count = N_ijc[parentNode].get(key);
            count += 1;
            N_ijc[parentNode].replace(key, count);
        } else {
            N_ijc[parentNode].put(key,1);
        }
    }


    /**
     * Auxiliary method for the toString re-definition
     * @return      A String with the Nijkc
     */
    private String printNijkc(){
        String a1 = "Nijkc:\n";
        a1 += "Son:" + categoryName + "\n";
        for(int i = 0; i < N_ijkc.length; i++) {
            a1+= "Father: " + i + "\n";
            Iterator it = N_ijkc[i].entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                a1 += pair.getKey() + " : " + pair.getValue() + "\n";
            }
        }
        return a1;
    }

    /**
     * Auxiliary method for the toString re-definition
     * @return      A String with the Nijc
     */
    private String printNijc() {
        String a1 = "Nijc:\n";
        a1 += "Son:" + categoryName + "\n";
        for(int i = 0; i < N_ijc.length; i++) {
            a1+= "Father: " + i + "\n";
            Iterator it =  N_ijc[i].entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                a1 += pair.getKey() + " : " + pair.getValue() + "\n";
            }
        }
        return a1;
    }

    /**
     * Auxiliary method for the toString re-definition
     * @return      A String with the Nikc
     */
    private String printNikc() {
        String a1 = "Nikc:\n";
        a1 += "Son:" + categoryName + "\n";
        Iterator it =  N_ikc.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            a1 += pair.getKey() + " : " + pair.getValue() + "\n";
        }
        return a1;
    }

    /**
     * Redefinition of the toString() method. Prints the values of Nijkc, Nijc and Nikc
     * @return      A String with the relevant values
     */
    @Override
    public String toString() {
        String s1 = printNijkc() + "\n" + printNijc() + "\n" + printNikc() + "\n";
        return s1;
    }

    /**
     * Redefinition of the equals() method. Uses the total number of labels, the category name, the labels themselves,
     * the Nijkc, Nikc and Nijc counts
     * @param o     Another ModelCategory object
     * @return      A boolean with true of false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModelCategory)) return false;

        ModelCategory<?> that = (ModelCategory<?>) o;

        if (totalNumberLabels != that.totalNumberLabels) return false;
        if (!categoryName.equals(that.categoryName)) return false;
        if (!labels.equals(that.labels)) return false;
        if (!Arrays.equals(N_ijkc, that.N_ijkc)) return false;
        if (!Arrays.equals(N_ijc, that.N_ijc)) return false;
        return N_ikc.equals(that.N_ikc);
    }

    /**
     * Redefinition of the hashcode method. Uses the total number of labels, the category name, the labels themselves,
     * the Nijkc, Nikc and Nijc counts
     * @return      An int with the hashcode value
     */
    @Override
    public int hashCode() {
        int result = categoryName.hashCode();
        result = 31 * result + totalNumberLabels;
        result = 31 * result + labels.hashCode();
        result = 31 * result + Arrays.hashCode(N_ijkc);
        result = 31 * result + Arrays.hashCode(N_ijc);
        result = 31 * result + N_ikc.hashCode();
        return result;
    }
}
