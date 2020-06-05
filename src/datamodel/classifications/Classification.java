package datamodel.classifications;

import classifier.TreeAugmentedNayveBayes;
import datamodel.Model;

/**
 * An interface that exposes the services for saving Classification data of a given training set.
 * @param <T>       A generic type
 * @see datamodel.Model
 * @see datamodel.classifications.ModelClassfication
 * @see datamodel.categories.Category
 * @see datamodel.categories.ModelCategory
 * @see TreeAugmentedNayveBayes
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-06
 */
public interface Classification<T> extends Model<T> {

    /**
     * A method that when implemented should increment the number of counts for a given classification
     * @param label         A generic type with the classification
     */
    void incrementNcCount(T label);

    /**
     * A method that when implemented should increment the number of counts for a given classification given its index
     * in the list of classifications stored
     * @param labelIndex    An int with the index of the classification we want to increment the count
     */
    void incrementNcCount(int labelIndex);

    /**
     * A method that when implemented should return the number of counts for a given classification (given the index of that classification)
     * @param c         An int with the index of the classification
     * @return          An int with the counts for that classification
     */
    int getNcCount(int c);

    /**
     * A method that when implemented should return the number of counts for a given classification (given the classification itself)
     * @param label     A generic type with the classification
     * @return          An int with the counts for that classification
     */
    int getNcCount(T label);
}
