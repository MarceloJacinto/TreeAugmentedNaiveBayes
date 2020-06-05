package metrics;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class implements the ConfusionMatrix interface. Given a list of predictions and list of actual classifications it
 * generates a confusion matrix (a 2D matrix of int), a list of labels and an array of double with the percentage of
 * occurance of each class in the real classification data. This data can be acessed by invoking the methods
 * getConfusionMatrix, getLabels, getPercentageOfEachClass and getPercentageOfIndividualClass.
 *
 * THE CONFUSION MATRIX STANDARD USED WAS: LINE ARE THE PREDICTED AND COLUMN ARE THE TRUE CLASSES
 * @param <T>   a generic type for the class that must extend Comparable
 * @see ConfusionMatrix
 * @author Marcelo Jacinto, David Souto, Gonçalo Tomás
 * @version 1.0.0
 * @since 2020-05-03
 */
public class ConcreteConfusionMatrix<T extends Comparable<T>> implements ConfusionMatrix<T> {

    /**
     * The real confusion matrix
     */
    private int[][] confusionMatrix;

    /**
     * A list of labels for the columns/lines
     */
    private List<T> labels;

    /**
     * An array with the percentage of appearance in each class 100% - 1.0, 50% - 0.5
     */
    private double[] percentageOfEachClass;

    /**
     * Constructor of the ConfusionMatrix. Receives 2 lists of generic type: one with the predictions and another
     * with the real classifications
     * @param predicted     A list of generic predicted data
     * @param actual        A list of generic actual classification data
     */
    public ConcreteConfusionMatrix(List<T> predicted, List<T> actual) {

        /* Check if the size of the lists are valid */
        if(predicted.size() != actual.size())
            throw new IllegalArgumentException("The list sizes do not match");

        labels = new LinkedList<>();

        // Get the possible classifications
        generateLabels(predicted, actual);

        this.confusionMatrix = new int[labels.size()][labels.size()];

        // Get the confusion matrix
        calculateMatrix(predicted, actual);

        //Calculate the percentage of each class (to get the bias of the classifications)
        percentageOfEachClass = new double[labels.size()];
        calculatePercentageOfEachClass();
    }

    /**
     * A constructor of the ConfusionMatrix. Receives 3 lists of generic type: one with the predictions, another with
     * thre real classications and another with labels that the 2 previous lists can contain.
     * @param predicted         A list of generic predicted data
     * @param actual            A list of generic actual classification data
     * @param possibleLabels    A list of generic with the labels that the data can assume
     */
    public ConcreteConfusionMatrix(List<T> predicted, List<T> actual, List<T> possibleLabels) {
        /* Check if the size of the lists are valid */
        if(predicted.size() != actual.size())
            throw new IllegalArgumentException("The list sizes do not match");

        labels = possibleLabels;
        labels.sort(T::compareTo);

        // Get the confusion matrix
        calculateMatrix(predicted, actual);
    }

    /**
     * A private method used to generate a list of labels given a list of predictions and a list of classifications.
     * This method will run through both list and save the labels that where found for the first time in each list.
     * Then it will order them using the standard compareTo() method implemented by the generic type.
     * @param predicted         A list of generic predicted data
     * @param actual            A list of generic actual classification data
     */
    private void generateLabels(List<T> predicted, List<T> actual) {

        /* Check if the size of the lists are valid */
        if(predicted.size() != actual.size())
            throw new IllegalArgumentException("The list sizes do not match");

        Iterator<T> it ;
        int i = 0;

        /* Search for the possible classifications */
        for(i = 0; i < 2; i++) {
            it = (i == 0) ? predicted.iterator() : actual.iterator();
            while(it.hasNext()) {
                T p = it.next();
                if(!labels.contains(p)) labels.add(p);
            }
        }
        labels.sort(T::compareTo);
    }

    /**
     * This private method will generate a matrix of int[][] given a list of predicted values and a list of true
     * classifications. This method will go accross the both lists and increment the matrix count according to
     * their index in the list of labels. This method already assumes that the list of labels of this class is already set.
     * @param predicted         A list of generic predicted data
     * @param actual            A list of generic actual classification data
     */
    private void calculateMatrix(List<T> predicted, List<T> actual) {

        /* Check if the size of the lists are valid */
        if(predicted.size() != actual.size())
            throw new IllegalArgumentException("The list sizes do not match");

        int size = predicted.size();

        Iterator pdIt = predicted.iterator();
        Iterator acIt = actual.iterator();

        while(pdIt.hasNext()) {
            T pdLabel = (T) pdIt.next();
            T acLabel = (T) acIt.next();
            int pd = labels.indexOf(pdLabel);
            int ac = labels.indexOf(acLabel);

            if(pd == -1 || ac == -1)
                throw new IllegalArgumentException("The list contain a label that is not in the list of possible labels");

            confusionMatrix[pd][ac] += 1;
        }
    }

    /**
     * Calculates the percentage of appearance of each class by counting in the confusion matrix the number of appearances
     * in each class. Then saves it and normalizes it in the percentageOfEachClass array.
     */
    private void calculatePercentageOfEachClass() {
        double total = 0;
        for(int i = 0; i < this.percentageOfEachClass.length; i++) {
            for(int j = 0; j < this.percentageOfEachClass.length; j++) {
                this.percentageOfEachClass[i] += this.confusionMatrix[j][i];
                total += this.confusionMatrix[j][i];
            }
        }

        for(int i = 0; i < this.percentageOfEachClass.length; i++)
            this.percentageOfEachClass[i] /= total;
    }

    /**
     * This method returns the actual confusion matrix (an int[][] matrix) with the lines symbolizing the number of
     * predictions for each class and the columns symbolizing the number of each class real appearance in the data.
     * @return      A int[][] with the confusion matrix
     */
    @Override
    public int[][] getConfusionMatrix() {
        return this.confusionMatrix;
    }

    /**
     * This method returns a list of generic type with the labels corresponding to each line/col of data in the confusion
     * matrix.
     * @return      A List of generic type with labels
     */
    @Override
    public List<T> getLabels() {
        return this.labels;
    }

    /**
     * This method returns the percentage of appearance of each class in the true classification data.
     * @return      An array of Double with the percentage of appearance of each class
     */
    @Override
    public double[] getPercentageOfEachClass(){
        return this.percentageOfEachClass;
    }

    /**
     * This method returns the percentage of appearance of a specific class given its index i
     * @param i     An int with the index to which you want to get the percentage from
     * @return      A Double with the percentage of appearance of that class in the real classification data
     */
    @Override
    public double getPercentageOfIndividualClass(int i) {
        //Check if "i" is inside bounds (i <= numberOfDifferentClasses)
        if (i >= this.labels.size() ) throw new IndexOutOfBoundsException("There are only " + this.labels.size() + " classes");
        return this.percentageOfEachClass[i];
    }

    /**
     * Redefinition of the toString() function that prints the matrix in the terminal along with the corresponding labels
     * @return      A String with the labels and the confusion matrix
     */
    @Override
    public String toString() {
        String aux = "ConfusionMatrix: \n" + "Lines are predicted and columns are true class \nlabels = " + labels + '\n' + "matrix: \n";
        for(int i = 0; i < confusionMatrix.length; i++) {
            for(int j = 0; j < confusionMatrix[i].length; j++) {
                aux += confusionMatrix[i][j] + " ";
            }
            aux += '\n';
        }
        //System.out.println(confusionMatrix.length);
        return aux;
    }

    /**
     * Redefinition of the equals() method. Uses the matrix of int[][] (the actual confusion matrix), the labels of the matrix
     * and the percentageOfEachclass to make the comparison
     * @param o     Another ConcreteConfusionMatrix object
     * @return      A boolean with true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConcreteConfusionMatrix)) return false;

        ConcreteConfusionMatrix<?> that = (ConcreteConfusionMatrix<?>) o;

        if (!Arrays.deepEquals(confusionMatrix, that.confusionMatrix)) return false;
        if (!labels.equals(that.labels)) return false;
        return Arrays.equals(percentageOfEachClass, that.percentageOfEachClass);
    }

    /**
     * Redefinition of the hashCode() method. Uses the matrix of int[][] (the actual confusion matrix), the labels of the matrix
     * and the percentageOfEachclass
     * @return      An int with the hashcode
     */
    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(confusionMatrix);
        result = 31 * result + labels.hashCode();
        result = 31 * result + Arrays.hashCode(percentageOfEachClass);
        return result;
    }
}
