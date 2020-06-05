package parser.facades;

import exceptions.EmptyStringException;
import exceptions.InvalidNumberOfFeaturesException;
import exceptions.RepeatedLabelException;
import parser.DataHolder;
import datamodel.DataModel;
import datamodel.TrainingModel;

import java.util.Arrays;

/**
 * A class that implements the DataHolder interface. Once the readLabels method is called a new TrainingModel
 * is instantiated. This training model receives the labels passed as parameters to the readLabels method. Every time
 * the readClass method is invoked, the trainingModel.updateModel is invoked with the data passed as argument to the
 * readClass method. This class is a facade ("a middle man") that makes the interface between receiving data from
 * some source, for example a csv file and obtaining a DataModel with useful counts from that data.
 * @see parser.DataHolder
 * @see datamodel.DataModel
 * @see datamodel.TrainingModel
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public class TrainingModelFacade implements DataHolder<String> {

    /**
     * A Datamodel to save countings extracted from the data
     * @see datamodel.DataModel
     * @see datamodel.TrainingModel
     */
    private DataModel<String> trainingModel;

    /**
     * The number of lines read
     */
    private int lineNumber = 0;

    /**
     * A method that when called, receives an array of String as parameters, and call the method DataModel.updateModel()
     * on the DataModel previously instantiated using the readLabel() method. This method assumes that the method
     * readLabels was invoked at least once. If not, the DataModel was not instantiated and the data being passed
     * to this method is ignored.
     * @param data      An array of String with features and a class
     * @exception       EmptyStringException if the array of String was empty
     */
    @Override
    public void readData(String[] data) {
        this.lineNumber++;
        if(data.length == 0) throw new EmptyStringException("Array of String was empty");

        if(trainingModel != null) {
            try {
                /* check if some of the String is empty "", if so, discard this*/
                for(int i = 0; i < data.length; i++)
                    if (data[i].equals("")) throw new InvalidNumberOfFeaturesException("There are empty features or classification");

                trainingModel.updateModel(data);
            } catch(InvalidNumberOfFeaturesException e) {
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
    }

    /**
     * A method that when called, receives an array of String with labels for the data. These labels are used to
     * instantiate a new TrainingModel, ignoring the last element of the data array.
     * @param data      An array of String with labels and a class label
     * @exception       EmptyStringException if the array of String was empty
     */
    @Override
    public void readLabels(String[] data) {
        this.lineNumber++;
        if(data.length == 0) throw new EmptyStringException("Array of String was empty");

        String[] labels = Arrays.copyOfRange(data, 0, data.length-1);
        try {
            trainingModel = new TrainingModel<String>(labels);
        } catch (RepeatedLabelException e) {
            System.out.println("In the first line of the training file exist repeated attributes/categories. Not allowed!");
            System.exit(1);
        }
    }

    /**
     * Method that return the DataModel object created and updated after using the methods readLabels and readData.
     * @see datamodel.DataModel
     * @see datamodel.TrainingModel
     * @return          returns a datamodel.DataModel with updated counts
     */
    public DataModel<String> getTrainingModel() {
        return trainingModel;
    }

    /**
     * Redefinition of the equals() method. Makes use of the line number and the trainingModel created to
     * make the comparison
     * @param o     Another TrainingModelFacade object
     * @return      A boolean with true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainingModelFacade)) return false;

        TrainingModelFacade that = (TrainingModelFacade) o;

        if (lineNumber != that.lineNumber) return false;
        return trainingModel.equals(that.trainingModel);
    }

    /**
     * Redefinition of the hashCode() method. Makes use of the line number and the trainingModel created to
     * make the hashcode
     * @return      An int with the hashcode
     */
    @Override
    public int hashCode() {
        int result = trainingModel.hashCode();
        result = 31 * result + lineNumber;
        return result;
    }
}
