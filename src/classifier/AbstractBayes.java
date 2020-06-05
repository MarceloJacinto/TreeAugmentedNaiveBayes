package classifier;

import datamodel.DataModel;
import score.Score;

/**
 * An abstract class that serves as a possible blue print for a Bayes Classifier. This class stores a DataModel
 * with counts taken from data and Score that can be used to evaluate the data with. This abstract class extends
 * the AbstractClassifier class that itself implements the Classifier interface.
 * @param <T>       A generic type
 * @see Classifier
 * @see AbstractClassifier
 * @see TreeAugmentedNayveBayes
 * @see score.Score
 * @see score.MDLScore
 * @see score.LLScore
 * @see datamodel.DataModel
 * @see datamodel.TrainingModel
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public abstract class AbstractBayes<T> extends AbstractClassifier<T> {

    /**
     * A Score to be used to calculate weights between nodes
     * @see score.Score
     * @see score.MDLScore
     * @see score.LLScore
     */
    protected Score score;

    /**
     * A DataModel of the same generic type of the classifier with counts and labels extracted from training data
     * @see datamodel.DataModel
     * @see datamodel.TrainingModel
     */
    protected DataModel<T> trainingModel;

    /**
     * The constructor for the Abstract Bayes. Receives as input a Score to be used and a DataModel with countings
     * extracted from training data
     * @param score             A Score with to be used to calculate weighted between edges in a probabilist model
     * @param trainingModel     A DataModel with counts extracted from training data
     * @see score.Score
     * @see score.MDLScore
     * @see score.LLScore
     * @see datamodel.DataModel
     * @see datamodel.TrainingModel
     */
    public AbstractBayes(Score score, DataModel<T> trainingModel) {
        if(score == null || trainingModel == null || trainingModel.getNCount() == 0)
            throw new IllegalArgumentException("Score or DataModel not set");
        this.score = score;
        this.trainingModel = trainingModel;
    }

    /**
     * A method that invokes the doTrain() method inside. The goal is to train a classifier using the data
     * inside the DataModel object
     */
    @Override
    public void train(){
        doTrain();
    }

    /**
     * A method that invokes the doPredict() method inside. The goal is to generate a prediction using the model
     * previously trained and the data received as parameter
     * @param data      An array of data of generic type. This data should be used to make a prediction
     * @return          A generic type with the prediction
     */
    @Override
    public T predictOneExample(T[] data) {
        return doPredict(data);
    }

    /**
     * An abstract method to be implemented a posterior that trains a model based on the data in the DataModel
     */
    protected abstract void doTrain();

    /**
     * Ab abstract method to be implemented a posteriori that makes a prediction based on the model trained
     * @param data      An array of data of generic type (to make a prediction with)
     * @return          A generic type with the prediction
     */
    protected abstract T doPredict(T[] data);

}
