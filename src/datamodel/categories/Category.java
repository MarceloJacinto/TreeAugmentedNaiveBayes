package datamodel.categories;

import classifier.TreeAugmentedNayveBayes;
import datamodel.Model;

/**
 * An interface that exposes the services for saving categories of data and its counts. For example
 * "Color" could be a category, and can have the labels "blue", "green" and "yellow". We also assume that there is a
 * fixed number of categories and that every category is related with each other (ex: combination of "Shape" and "Color")
 * kind of like relationships between father and son
 * @param <T>       A generic type
 * @see datamodel.Model
 * @see datamodel.classifications.ModelClassfication
 * @see datamodel.categories.ModelCategory
 * @see TreeAugmentedNayveBayes
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-06
 */
public interface Category<T> extends Model<T> {

    /**
     * A method that when implemented should return the category name (ex: "Color")
     * @return      A String with the category name
     */
    String getCategoryName();

    /**
     * A method that should return the index of a certain label of the category (ex: index of the label "green")
     * @param label     A generic type with the label
     * @return          An int with the index
     */
    int getIndexOfLabel(T label);

    /**
     * A method that should return the N_ijkc counts
     * @param i             An int with the node index that we are considering
     * @param j             An int with the configuration of the parent of that node
     * @param k             An int with the configuration of the node
     * @param c             An int with the configuration of the classification
     * @param parentNode    An int with the parent of the node
     * @return              An int with the counts for that combination
     */
    int getNijkc(int i, int j, int k, int c, int parentNode);

    /**
     * A method that should return the N_ikc count
     * @param i             An int with the node index that we are considering
     * @param k             An int with the configuration of the node
     * @param c             An int with the configuration of the classification
     * @return              An int with the counts for that combination
     */
    int getNikc(int i, int k, int c);

    /**
     * A method that should return the N_ijc count
     * @param i             An int with the node index that we are considering
     * @param j             An int with the configuration of the parent of that node
     * @param c             An int with the configuration of the classification
     * @param parentNode    An int with the parent of the node
     * @return              An int with the counts for that combination
     */
    int getNijc(int i, int j, int c, int parentNode);

    /**
     * A method that should increment the Nikc count
     * @param i             An int with the node index that we are considering
     * @param k             An int with the configuration of the node
     * @param c             An int with the configuration of the classification
     */
    void incrementNikc(int i, int k, int c);

    /**
     * A method that should increment the Nijkc count
     * @param i             An int with the node index that we are considering
     * @param j             An int with the configuration of the parent of that node
     * @param k             An int with the configuration of the node
     * @param c             An int with the configuration of the classification
     * @param parentNode    An int with the parent of the node
     */
    void incrementNijkc(int i, int j, int k, int c, int parentNode);

    /**
     * A method that should increment the Nijc count
     * @param i             An int with the node index that we are considering
     * @param j             An int with the configuration of the parent of that node
     * @param c             An int with the configuration of the classification
     * @param parentNode    An int with the parent of the node
     */
    void incrementNijc(int i, int j, int c, int parentNode);
}
