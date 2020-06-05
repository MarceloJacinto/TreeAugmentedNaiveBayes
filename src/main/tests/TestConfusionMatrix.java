package main.tests;

import metrics.ConcreteConfusionMatrix;

import java.util.Arrays;
import java.util.List;

/**
 * CLASS USED ONLY FOR TESTING PURPOSES - THIS IS BASICALLY A UNIT TEST OF THE CODE
 * A class used to test the ConfusionMatrix class
 */
public class TestConfusionMatrix {
    public static void main(String[] args) {

        List<String> actual = Arrays.asList(new String[]{"0", "1", "0", "2", "3", "4"});
        List<String> predicted = Arrays.asList(new String[]{"0", "0", "1", "2", "0", "5"});

        ConcreteConfusionMatrix<String> confusionMatrix = new ConcreteConfusionMatrix<>(predicted, actual);
        System.out.println(confusionMatrix.toString());
        System.out.println(confusionMatrix.getPercentageOfIndividualClass(5));
        double[] percentages = confusionMatrix.getPercentageOfEachClass();
        for(Double singlePercentage : percentages)
            System.out.print(singlePercentage + " ");
    }
}
