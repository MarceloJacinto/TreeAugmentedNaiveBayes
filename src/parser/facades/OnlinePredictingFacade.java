package parser.facades;

import classifier.Classifier;
import exceptions.EmptyStringException;
import exceptions.InvalidNumberOfFeaturesException;
import parser.DataHolder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * A class that implements the DataHolder interface. This class receives a Classifier object on it's constructor, that
 * must be instantiated. Every time the method readData is called, the classifier.predictOneExample method is called to
 * predict a class given the data received in the readData method. The result classification is added to a List of
 * String and the true classification is saved on a separate list of String. The lists with the trueClasses and the
 * predicted results can be accesses by invoking the methods getTrueClasses() an getResults().
 * This class is a facade ("a middle man") that makes the interface between receiving data from some source, for
 * example a csv file and calling a classifier to make predictions on the data.
 * @see parser.DataHolder
 * @see classifier.Classifier
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public class OnlinePredictingFacade implements DataHolder<String> {

    /**
     * A Classifier to make classifications with
     * @see classifier.Classifier
     * @see classifier.TreeAugmentedNayveBayes
     */
    private Classifier<String> classifier;

    /**
     * A List of String with the real classes extracted from the data
     */
    private List<String> trueClasses = new LinkedList<String>();

    /**
     * A List of String with the results of the predictions
     */
    private List<String> results = new LinkedList<String>();

    /**
     * The number of lines read
     */
    private int lineNumber = 0;

    /**
     * The constructor of OnlinePredictingFacade, that receives a Classifier object that must be instantiated
     * @param classifier        The classifier object to make predictions with
     */
    public OnlinePredictingFacade(Classifier<String> classifier) {
        this.classifier = classifier;
        if(classifier == null) throw new NullPointerException("Classifier is not set");
    }

    /**
     * A method that when called, received an array of String as parameters, saves the last element in a List of String
     * of trueClasses and calls the classifier.predictOneExample method with the rest of the String array. The result
     * that comes from the classifier is saved in a List of String results.
     * @param data      An array of String with features and a class
     */
    @Override
    public void readData(String[] data) {
        this.lineNumber++;
        if(data.length == 0) throw new EmptyStringException("String was empty");

        /* get only the feature data that is needed for the prediction */
        String[] predictionData = Arrays.copyOfRange(data,   0, data.length-1);

        /* make the prediction and save it in a protected manner */
        try {
            /* check if some of the String is empty "", if so, discard this*/
            for(int i = 0; i < data.length; i++)
                if (data[i].equals("")) throw new InvalidNumberOfFeaturesException("There are empty features or classification");

            String pred = classifier.predictOneExample(predictionData);
            if(pred != null) {
                results.add(pred);
                /* save the true class in the list */
                trueClasses.add(data[data.length-1]);
            } else {
                System.out.println("Could not make a prediction for data in line " + this.lineNumber + ". Maybe it contains a feature" +
                        " that was never seen during training?!");
            }
        }catch(InvalidNumberOfFeaturesException e){
            boolean first = true;
            System.out.print("(Invalid number of features) Ignoring line " + this.lineNumber + " with data: ");
            for(String one: data) {
                if (first) {
                    System.out.print(one);
                    first = false;
                }else {
                    System.out.print(", " + one);
                }
            }
            System.out.print("\n");
        }

    }

    /**
     * A method that ignores the data being received as the labels are not necessary in this use-case
     * @param data      An array of String
     */
    @Override
    public void readLabels(String[] data) {
        // we have no interest in reading the labels when making classifications
        // so just return
        this.lineNumber++;
        return;
    }

    /**
     * A method that returns a List of String with the predictions made with the classifier
     * @return      a List of String with predictions
     */
    public List<String> getResults() {
        return results;
    }

    /**
     * A method that returns a List of String with the real classes read
     * @return      a List of String with the true classes
     */
    public List<String> getTrueClasses() {
        return trueClasses;
    }

    /**
     * Redefinition of the equals() method. Makes use of the number of lines read, the classifier object, the list of true classes
     * in the data and the list of results from the predictions.
     * @param o     Another OnlinePredictingFacade object
     * @return      A boolean with true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OnlinePredictingFacade)) return false;

        OnlinePredictingFacade that = (OnlinePredictingFacade) o;

        if (lineNumber != that.lineNumber) return false;
        if (!classifier.equals(that.classifier)) return false;
        if (!trueClasses.equals(that.trueClasses)) return false;
        return results.equals(that.results);
    }

    /**
     * Redefinition of the hashCode() method. Makes use of the number of lines read, the classifier object, the list of true classes
     * in the data and the list of results from the predictions.
     * @return      An int with the hashcode
     */
    @Override
    public int hashCode() {
        int result = classifier.hashCode();
        result = 31 * result + trueClasses.hashCode();
        result = 31 * result + results.hashCode();
        result = 31 * result + lineNumber;
        return result;
    }
}
